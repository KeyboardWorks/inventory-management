package keyboard.works.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import keyboard.works.entity.InventoryTransactionInItem;
import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.InventoryType;
import keyboard.works.entity.Product;
import keyboard.works.entity.ProductInOutTransaction;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.service.ProductPackagingService;
import keyboard.works.service.ProductService;

@SpringBootTest
@Sql(scripts = {"/sql/product-delete.sql", "/sql/product-insert.sql",
		"/sql/product-category-delete.sql", "/sql/product-category-insert.sql",
		"/sql/unit-of-measure-delete.sql", "/sql/unit-of-measure-insert.sql"})
public class ProductInOutTransactionFactoryTest {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductPackagingService productPackagingService;
	
	private Product product;
	
	private ProductPackaging productPackaging;
	
	@BeforeEach
	public void setUp() {
		product = productService.getProduct("002");
		productPackaging = productPackagingService.getProductPackaging("002");
	}
	
	@Test
	public void createInTransactionTest() {
		
		InventoryTransactionItemExample inventoryTransactionItem = createInventoryTransactionItem(new BigDecimal(5), new BigDecimal(5_000));
		
		ProductInOutTransaction productInOutTransaction = ProductInOutTransactionFactory.createInTransaction(inventoryTransactionItem);
		
		assertNotNull(productInOutTransaction);
		assertEquals(inventoryTransactionItem.getDate(), productInOutTransaction.getDate());
		assertEquals(inventoryTransactionItem.getType(), productInOutTransaction.getType());
		assertEquals(inventoryTransactionItem.getPrice(), productInOutTransaction.getPrice());
		assertEquals(inventoryTransactionItem.getReceipted(), productInOutTransaction.getQuantity());
		assertEquals(inventoryTransactionItem.getReceipted().multiply(productPackaging.getQuantityToBase()).setScale(2), productInOutTransaction.getQuantityLeft());
		assertEquals(inventoryTransactionItem.getProduct(), productInOutTransaction.getProduct());
		assertEquals(inventoryTransactionItem.getProductPackaging(), productInOutTransaction.getProductPackaging());
		assertEquals(inventoryTransactionItem, productInOutTransaction.getInventoryTransactionItem());
		
	}
	
	public InventoryTransactionItemExample createInventoryTransactionItem(BigDecimal receipted, BigDecimal price) {
		
		InventoryTransactionItemExample inventoryTransactionItem = new InventoryTransactionItemExample();
		
		inventoryTransactionItem.setProduct(product);
		inventoryTransactionItem.setProductPackaging(productPackaging);
		inventoryTransactionItem.setReceipted(receipted);
		
		return inventoryTransactionItem;
	}
	
	class InventoryTransactionItemExample extends InventoryTransactionItem implements InventoryTransactionInItem {

		@Override
		public BigDecimal getPrice() {
			return new BigDecimal(10_000);
		}

		@Override
		public LocalDate getDate() {
			return LocalDate.now();
		}

		@Override
		public InventoryType getType() {
			return InventoryType.IN;
		}

	}
	
}
