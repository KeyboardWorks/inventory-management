package keyboard.works.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import keyboard.works.entity.Product;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.model.request.ProductRequest;

@SpringBootTest
@Sql(scripts = {"/sql/product-delete.sql", "/sql/product-insert.sql",
		"/sql/product-category-delete.sql", "/sql/product-category-insert.sql",
		"/sql/unit-of-measure-delete.sql", "/sql/unit-of-measure-insert.sql"})
@Transactional
public class ProductServiceTest {

	@Autowired
	private ProductService productService;
	
	@Test
	public void getProductsTest() {
		
		List<Product> products = productService.getProducts();
		
		assertNotNull(products);
		assertEquals(2, products.size());
		
		for(Product product : products) {
			assertNotNull(product.getPackagings());
			assertEquals(1, product.getPackagings().size());
		}
	}
	
	@Sql(scripts = "/sql/product-delete.sql")
	public void getProducts_EmptyTest() {
		
		List<Product> products = productService.getProducts();
		
		assertNotNull(products);
		assertEquals(0, products.size());
		
	}
	
	@Test
	public void getProductTest() {
		
		Product product = productService.getProduct("001");
		
		assertNotNull(product);
		assertNotNull(product.getId());
		
	}
	
	@Test
	public void getProduct_NotFoundTest() {
		
		assertThrows(EntityNotFoundException.class, () -> {
			productService.getProduct("003");
		});
		
	}
	
	@Test
	public void createProductTest() {
		
		ProductRequest request = new ProductRequest();
		request.setCode("Product Code 3");
		request.setName("Product Name 3");
		request.setProductCategory("001");
		
		Product product = productService.createProduct(request);
		
		assertNotNull(product);
		assertNotNull(product.getId());
		assertNotNull(product.getPackagings());
		assertEquals(1, product.getPackagings().size());
		
		for(ProductPackaging productPackaging : product.getPackagings()) {
			assertNotNull(productPackaging.getUnitOfMeasure());
			assertEquals(BigDecimal.ONE, productPackaging.getQuantityToBase());
			assertEquals("PCS", productPackaging.getUnitOfMeasure().getCode());
		}
		
	}
	
	@Test
	public void createProduct_ProductCategoryNotFoundTest() {
		
		ProductRequest request = new ProductRequest();
		request.setCode("Product Code 3");
		request.setName("Product Name 3");
		request.setProductCategory("003");
		
		assertThrows(EntityNotFoundException.class, () -> {
			productService.createProduct(request);
		});
		
	}
	
	@Test
	public void createProduct_ValidationTest() {
		
		ProductRequest productRequest = new ProductRequest();
		
		assertThrows(ConstraintViolationException.class, () -> {
			productService.createProduct(productRequest);
		});
		
	}
	
	@Test
	public void updateProductTest() {
		
		ProductRequest request = new ProductRequest();
		request.setCode("Product Code 3");
		request.setName("Product Name 3");
		request.setProductCategory("001");
		
		Product product = productService.updateProduct("001", request);
		
		assertNotNull(product);
		assertNotNull(product.getId());
		assertEquals("Product Code 3", product.getCode());
		assertEquals("Product Name 3", product.getName());
		assertEquals("001", product.getProductCategory().getId());
	}
	
	@Test
	public void updateProduct_NotFoundTest() {
		
		ProductRequest request = new ProductRequest();
		request.setCode("Product Code 3");
		request.setName("Product Name 3");
		request.setProductCategory("001");
		
		assertThrows(EntityNotFoundException.class, () -> {
			productService.updateProduct("003", request);
		});
		
	}
	
	@Test
	public void updateProduct_ValidationTest() {
		
		ProductRequest request = new ProductRequest();
		
		assertThrows(ConstraintViolationException.class, () -> {
			productService.updateProduct("001", request);
		});
		
	}
	
	@Test
	public void deleteProductTest() {
		
		productService.deleteProduct("001");

		assertThrows(EntityNotFoundException.class, () -> {
			productService.getProduct("001");
		});
		
	}
	
	@Test
	public void deleteProduct_NotFoundTest() {
		
		assertThrows(EntityNotFoundException.class, () -> {
			productService.deleteProduct("003");
		});
		
	}
	
}
