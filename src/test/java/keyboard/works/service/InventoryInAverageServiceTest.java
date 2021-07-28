package keyboard.works.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import keyboard.works.entity.GoodsReceipt;
import keyboard.works.entity.GoodsReceiptItem;
import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.InventoryType;
import keyboard.works.entity.Product;
import keyboard.works.entity.ProductAveragePrice;
import keyboard.works.entity.ProductInOutTransaction;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.repository.GoodsReceiptRepository;
import keyboard.works.repository.ProductAveragePriceRepository;
import keyboard.works.repository.ProductInOutTransactionRepository;

@SpringBootTest
@Sql(scripts = {"/sql/product-delete.sql", "/sql/product-insert.sql",
		"/sql/product-category-delete.sql", "/sql/product-category-insert.sql",
		"/sql/unit-of-measure-delete.sql", "/sql/unit-of-measure-insert.sql",
		"/sql/goods-receipt-delete.sql",
		"/sql/product-average-price-delete.sql",
		"/sql/product-in-out-transaction-delete.sql"})
public class InventoryInAverageServiceTest {

	@Autowired
	@Qualifier("inventoryInAverageServiceImpl")
	private InventoryInService inventoryInService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductPackagingService productPackagingService;
	
	@Autowired
	private GoodsReceiptRepository goodsReceiptRepository;
	
	@Autowired
	private ProductAveragePriceRepository productAveragePriceRepository;
	
	@Autowired
	private ProductInOutTransactionRepository productInOutTransactionRepository;
	
	private Product product;
	
	private ProductPackaging productPackaging;
	
	@BeforeEach
	public void setUp() {
		product = productService.getProduct("001");
		productPackaging = productPackagingService.getProductPackaging("001");
	}
	
	@Test
	public void isSupportTest() {
		assertTrue(inventoryInService.isSupport(InventoryMethodType.AVERAGE));
	}
	
	@Test
	public void execute_NoAverageTransaction() {
		
		insertGoodsReceipt("GR 001", new BigDecimal(5), new BigDecimal(5_000));
		
		ProductAveragePrice averageTransaction = productAveragePriceRepository
				.findByProductAndProductPackaging(product, productPackaging)
				.orElseThrow(EntityNotFoundException::new);
		
		assertNotNull(averageTransaction);
		assertNotNull(averageTransaction.getId());
		assertNotNull(averageTransaction.getProduct());
		assertNotNull(averageTransaction.getProductPackaging());
		assertEquals(new BigDecimal(5).setScale(2), averageTransaction.getQuantity());
		assertEquals(new BigDecimal(5_000).setScale(2), averageTransaction.getPrice());
		
		List<ProductInOutTransaction> productInOutTransactions = productInOutTransactionRepository.findAll();
		assertNotNull(productInOutTransactions);
		assertEquals(1, productInOutTransactions.size());
		
		productInOutTransactions.forEach(productInOutTransaction -> {
			assertNotNull(productInOutTransaction);
			assertNotNull(productInOutTransaction.getId());
			assertEquals(InventoryType.IN, productInOutTransaction.getType());
			assertEquals(BigDecimal.ZERO.setScale(2), productInOutTransaction.getQuantityLeft());
			assertEquals(new BigDecimal(5).setScale(2), productInOutTransaction.getQuantity());
			assertEquals(new BigDecimal(5_000).setScale(2), productInOutTransaction.getPrice());
		});
	}
	
	@Test
	public void execute_UpdateExistingAverageTransaction() {
		
		BigDecimal quantity1 = new BigDecimal(5);
		BigDecimal price1 = new BigDecimal(5_000);
		
		BigDecimal quantity2 = new BigDecimal(10);
		BigDecimal price2 = new BigDecimal(10_000);
		
		insertGoodsReceipt("GR 001", quantity1, price1);
		insertGoodsReceipt("GR 002", quantity2, price2);
		
		ProductAveragePrice averageTransaction = productAveragePriceRepository
				.findByProductAndProductPackaging(product, productPackaging)
				.orElseThrow(EntityNotFoundException::new);
		
		BigDecimal totalQuantity = quantity1.add(quantity2);
		BigDecimal averagePrice = quantity1.multiply(price1).add(quantity2.multiply(price2)).divide(totalQuantity, 2, RoundingMode.HALF_UP);
		
		assertNotNull(averageTransaction);
		assertNotNull(averageTransaction.getId());
		assertNotNull(averageTransaction.getProduct());
		assertNotNull(averageTransaction.getProductPackaging());
		assertEquals(totalQuantity.setScale(2), averageTransaction.getQuantity());
		assertEquals(averagePrice.setScale(2), averageTransaction.getPrice());
		
		List<ProductInOutTransaction> productInOutTransactions = productInOutTransactionRepository.findAll();
		assertNotNull(productInOutTransactions);
		assertEquals(2, productInOutTransactions.size());
		
		productInOutTransactions.forEach(productInOutTransaction -> {
			assertNotNull(productInOutTransaction);
			assertNotNull(productInOutTransaction.getId());
			assertEquals(InventoryType.IN, productInOutTransaction.getType());
			assertEquals(BigDecimal.ZERO.setScale(2), productInOutTransaction.getQuantityLeft());
		});
	}
	
	@Test
	public void execute_ConcurrentUser() throws InterruptedException {
		
		insertGoodsReceipt("GR 001", new BigDecimal(5), new BigDecimal(5_000));
		
		for (int i = 0; i < 9; i++) {
			new Thread(() -> {
				insertGoodsReceipt(Thread.currentThread().getName(), new BigDecimal(5), new BigDecimal(5_000));
			}).start();
		}
		
		Thread.sleep(1_000);
		
		ProductAveragePrice averageTransaction = productAveragePriceRepository
				.findByProductAndProductPackaging(product, productPackaging)
				.orElseThrow(EntityNotFoundException::new);
		
		assertNotNull(averageTransaction);
		assertNotNull(averageTransaction.getId());
		assertNotNull(averageTransaction.getProduct());
		assertNotNull(averageTransaction.getProductPackaging());
		assertEquals(new BigDecimal(50).setScale(2), averageTransaction.getQuantity());
		assertEquals(new BigDecimal(5_000).setScale(2), averageTransaction.getPrice());
		
		List<ProductInOutTransaction> productInOutTransactions = productInOutTransactionRepository.findAll();
		assertNotNull(productInOutTransactions);
		assertEquals(10, productInOutTransactions.size());
		
		productInOutTransactions.forEach(productInOutTransaction -> {
			assertNotNull(productInOutTransaction);
			assertNotNull(productInOutTransaction.getId());
			assertEquals(InventoryType.IN, productInOutTransaction.getType());
			assertEquals(BigDecimal.ZERO.setScale(2), productInOutTransaction.getQuantityLeft());
			assertEquals(new BigDecimal(5).setScale(2), productInOutTransaction.getQuantity());
			assertEquals(new BigDecimal(5_000).setScale(2), productInOutTransaction.getPrice());
		});
	}
	
	private void insertGoodsReceipt(String code, BigDecimal receipted, BigDecimal price) {
		
		GoodsReceipt goodsReceipt = new GoodsReceipt();
		goodsReceipt.setCode(code);
		goodsReceipt.setDate(LocalDate.now());
		
		GoodsReceiptItem goodsReceiptItem = new GoodsReceiptItem();
		goodsReceiptItem.setReceipted(receipted);
		goodsReceiptItem.setPrice(price);
		goodsReceiptItem.setProduct(product);
		goodsReceiptItem.setProductPackaging(productPackaging);
		goodsReceiptItem.setGoodsReceipt(goodsReceipt);
		
		goodsReceipt.setItems(new HashSet<>());
		goodsReceipt.getItems().add(goodsReceiptItem);
		
		GoodsReceipt storedGoodsReceipt = goodsReceiptRepository.save(goodsReceipt);
		
		storedGoodsReceipt.getItems()
			.forEach(inventoryInService::execute);
	}
	
}
