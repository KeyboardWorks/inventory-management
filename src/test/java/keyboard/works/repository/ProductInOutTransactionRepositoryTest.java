package keyboard.works.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = {"/sql/product-delete.sql", "/sql/product-insert.sql",
		"/sql/product-category-delete.sql", "/sql/product-category-insert.sql",
		"/sql/unit-of-measure-delete.sql", "/sql/unit-of-measure-insert.sql",
		"/sql/product-in-out-transaction-delete.sql", "/sql/product-in-out-transaction-insert.sql"})
public class ProductInOutTransactionRepositoryTest {

	@Autowired
	private ProductInOutTransactionRepository productInOutTransactionRepository;
	
	@Disabled
	@Test
	public void findAllTest() {
		productInOutTransactionRepository.findAll().forEach(inOut -> {
			System.out.println(inOut.getCreatedDateTime());
			System.out.println(inOut.getQuantity());
		});
	}
	
	@Disabled
	@Test
	public void availableStockTest() {
		
		productInOutTransactionRepository.findAvailableStock("001", "001", Sort.by(Order.desc("date"), Order.desc("createdDateTime")))
		.forEach(inOut -> {
			System.out.println(inOut.getDate());
			System.out.println(inOut.getCreatedDateTime());
			System.out.println(inOut.getQuantity());
		});;
		
	}
	
	@Test
	@Sql(scripts = "/sql/product-in-out-transaction-delete.sql")
	public void availableStock_EmptyInTest() {
		
		BigDecimal availableStock = productInOutTransactionRepository.getAvailableStock("001", "001");
		
		assertNotNull(availableStock);
		assertEquals(BigDecimal.ZERO, availableStock);
		
	}
	
}
