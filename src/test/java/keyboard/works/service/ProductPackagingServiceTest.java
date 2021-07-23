package keyboard.works.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import keyboard.works.entity.Product;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.model.request.ProductPackagingRequest;

@SpringBootTest
@Sql(scripts = {"/sql/product-delete.sql", "/sql/product-insert.sql",
		"/sql/unit-of-measure-delete.sql", "/sql/unit-of-measure-insert.sql"})
public class ProductPackagingServiceTest {
	
	@Autowired
	private ProductPackagingService productPackagingService;
	
	@Test
	public void createProductPackagingTest() {
		
		ProductPackagingRequest request = new ProductPackagingRequest();
		request.setQuantityToBase(BigDecimal.TEN);
		request.setProduct("001");
		request.setUnitOfMeasure("001");
		
		ProductPackaging productPackaging = productPackagingService.createProductPacking(request);
		
		assertNotNull(productPackaging);
		assertNotNull(productPackaging.getId());
		assertEquals(BigDecimal.TEN, productPackaging.getQuantityToBase());
		
		assertNotNull(productPackaging.getProduct());
		assertNotNull(productPackaging.getUnitOfMeasure());
		
		assertEquals("001", productPackaging.getProduct().getId());
		assertEquals("001", productPackaging.getUnitOfMeasure().getId());
		
	}
	
	@Test
	public void createProductPackaging_ProductNotFoundTest() {
		
		ProductPackagingRequest request = new ProductPackagingRequest();
		request.setQuantityToBase(BigDecimal.TEN);
		request.setProduct("003");
		request.setUnitOfMeasure("001");
	
		assertThrows(RuntimeException.class, () -> {
			productPackagingService.createProductPacking(request);
		});
		
	}
	
	@Test
	public void createProductPackaging_UnitOfMeasureNotFoundTest() {
		
		ProductPackagingRequest request = new ProductPackagingRequest();
		request.setQuantityToBase(BigDecimal.TEN);
		request.setProduct("001");
		request.setUnitOfMeasure("003");
		
		assertThrows(RuntimeException.class, () -> {
			productPackagingService.createProductPacking(request);
		});
		
	}
	
	@Test
	public void createProductPackaging_ValidationTest() {
		
		ProductPackagingRequest request = new ProductPackagingRequest();
		
		assertThrows(ConstraintViolationException.class, () -> {
			productPackagingService.createProductPacking(request);
		});
		
	}
	
	@Test
	public void updateProductPackagingTest() {
		
		ProductPackagingRequest request = new ProductPackagingRequest();
		request.setQuantityToBase(BigDecimal.TEN);
		request.setProduct("001");
		request.setUnitOfMeasure("001");
		
		ProductPackaging productPackaging = productPackagingService.updateProductPackaging("001", request);
		
		assertNotNull(productPackaging);
		assertNotNull(productPackaging.getProduct());
		assertNotNull(productPackaging.getUnitOfMeasure());
		assertEquals(BigDecimal.TEN, productPackaging.getQuantityToBase());
		
	}
	
	@Test
	public void updateProductPackaging_NotFoundTest() {
		
		ProductPackagingRequest request = new ProductPackagingRequest();
		request.setQuantityToBase(BigDecimal.TEN);
		request.setProduct("001");
		request.setUnitOfMeasure("001");
		
		assertThrows(RuntimeException.class, () -> {
			productPackagingService.updateProductPackaging("003", request);
		});
		
	}
	
	@Test
	public void updateProductPackaging_UnitOfMeasureNotFoundTest() {
		
		ProductPackagingRequest request = new ProductPackagingRequest();
		request.setQuantityToBase(BigDecimal.TEN);
		request.setProduct("001");
		request.setUnitOfMeasure("003");
		
		assertThrows(RuntimeException.class, () -> {
			productPackagingService.updateProductPackaging("001", request);
		});
		
	}
	
	@Test
	public void createDefaultProductPackagingTest() {
		
		Product product = new Product();
		product.setCode("Product Code");
		product.setName("Product Name");
		
		ProductPackaging productPackaging = productPackagingService.createDefaultProductPackaging(product);
		
		assertNotNull(productPackaging);
		assertNotNull(productPackaging.getProduct());
		assertEquals(product, productPackaging.getProduct());
		assertNotNull(productPackaging.getUnitOfMeasure());
		assertEquals("PCS", productPackaging.getUnitOfMeasure().getCode());
	}
	
}
