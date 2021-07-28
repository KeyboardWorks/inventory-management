package keyboard.works.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

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
import keyboard.works.entity.ProductInOutTransaction;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.repository.GoodsReceiptRepository;
import keyboard.works.repository.ProductInOutTransactionRepository;

@SpringBootTest
@Sql(scripts = {"/sql/product-delete.sql", "/sql/product-insert.sql",
		"/sql/product-category-delete.sql", "/sql/product-category-insert.sql",
		"/sql/unit-of-measure-delete.sql", "/sql/unit-of-measure-insert.sql",
		"/sql/goods-receipt-delete.sql",
		"/sql/product-in-out-transaction-delete.sql"})
public class InventoryInFifoLifoServiceTest {
	
	@Autowired
	@Qualifier("inventoryInFifoLifoServiceImpl")
	private InventoryInService inventoryInService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductPackagingService productPackagingService;
	
	@Autowired
	private GoodsReceiptRepository goodsReceiptRepository;
	
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
	public void isSupport_FifoTest() {
		
		assertTrue(inventoryInService.isSupport(InventoryMethodType.FIFO));
		
	}
	
	@Test
	public void isSupport_LifoTest() {
		
		assertTrue(inventoryInService.isSupport(InventoryMethodType.LIFO));
		
	}
	
	@Test
	public void executeTest() {
		
		insertGoodsReceipt("GR 001", new BigDecimal(5), new BigDecimal(5_000));
		
		List<ProductInOutTransaction> productInOutTransactions = productInOutTransactionRepository.findAll();
		assertNotNull(productInOutTransactions);
		assertEquals(1, productInOutTransactions.size());
		
		productInOutTransactions.forEach(productInOutTransaction -> {
			assertNotNull(productInOutTransaction);
			assertNotNull(productInOutTransaction.getId());
			assertEquals(InventoryType.IN, productInOutTransaction.getType());
			assertEquals(new BigDecimal(5).setScale(2), productInOutTransaction.getQuantityLeft());
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
