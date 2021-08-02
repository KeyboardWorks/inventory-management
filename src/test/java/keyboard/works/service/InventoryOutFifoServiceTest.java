package keyboard.works.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import keyboard.works.entity.GoodsIssue;
import keyboard.works.entity.GoodsIssueItem;
import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.InventoryType;
import keyboard.works.entity.Product;
import keyboard.works.entity.ProductInOutTransaction;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.repository.GoodsIssueRepository;
import keyboard.works.repository.ProductInOutTransactionRepository;

@SpringBootTest
@Sql(scripts = {"/sql/product-delete.sql", "/sql/product-insert.sql",
		"/sql/product-category-delete.sql", "/sql/product-category-insert.sql",
		"/sql/unit-of-measure-delete.sql", "/sql/unit-of-measure-insert.sql",
		"/sql/goods-issue-delete.sql",
		"/sql/product-in-out-transaction-delete.sql", "/sql/product-in-out-transaction-insert.sql"})
public class InventoryOutFifoServiceTest {

	@Autowired
	@Qualifier("inventoryOutFifoServiceImpl")
	private InventoryOutService inventoryOutService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductPackagingService productPackagingService;
	
	@Autowired
	private ProductInOutTransactionRepository productInOutTransactionRepository;
	
	@Autowired
	private GoodsIssueRepository goodsIssueRepository;
	
	private Product product;
	
	private ProductPackaging productPackaging;
	
	@BeforeEach
	public void setUp() {
		product = productService.getProduct("001");
		productPackaging = productPackagingService.getProductPackaging("001");
	}

	@Test
	public void isSupportTest() {
		assertTrue(inventoryOutService.isSupport(InventoryMethodType.FIFO));
	}

	@Test
	public void executeTest() {
		
		createGoodsIssue(new BigDecimal(17L));

		List<ProductInOutTransaction> outs = productInOutTransactionRepository.findByType(InventoryType.OUT);
		
		BigDecimal sumOut = outs.stream().map(ProductInOutTransaction::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		assertNotNull(outs);
		assertEquals(4, outs.size());
		assertEquals(new BigDecimal(17L).setScale(2), sumOut);
		
		for(ProductInOutTransaction out : outs) {
			assertNotNull(out.getDate());
			assertNotNull(out.getProduct());
			assertNotNull(out.getProductPackaging());
			assertNotNull(out.getPrice());
			assertNotNull(out.getInFrom());
			assertNotNull(out.getInventoryTransactionItem());
		}
		
	}
	
	@Test
	@Transactional
	public void execute_InsufficientStockTest() {
		
		assertThrows(RuntimeException.class, () -> {
			createGoodsIssue(new BigDecimal(50L));
		});
		
	}
	
	@Test
	public void execute_ConcurrentUser() throws InterruptedException {
		
		for (int i = 0; i < 10; i++) {
			
			new Thread(() -> {
				createGoodsIssue(new BigDecimal(2));
			})
			.start();
			
		}

		Thread.sleep(1_000);
		
		BigDecimal availableStock = productInOutTransactionRepository.getAvailableStock(product.getId(), productPackaging.getId());
		assertEquals(new BigDecimal(16L).setScale(2), availableStock);
		
	}
	
	private void createGoodsIssue(BigDecimal issued) {
		
		GoodsIssue goodsIssue = new GoodsIssue();
		goodsIssue.setCode(UUID.randomUUID().toString());
		goodsIssue.setDate(LocalDate.now());
		
		GoodsIssueItem goodsIssueItem = new GoodsIssueItem();
		goodsIssueItem.setProduct(product);
		goodsIssueItem.setProductPackaging(productPackaging);
		goodsIssueItem.setIssued(issued);
		goodsIssueItem.setGoodsIssue(goodsIssue);
		
		goodsIssue.setItems(new HashSet<>());
		goodsIssue.getItems().add(goodsIssueItem);
		
	 	GoodsIssue storedGoodsIssue = goodsIssueRepository.save(goodsIssue);
		
	 	storedGoodsIssue.getItems().forEach(inventoryOutService::execute);
	}
	
}
