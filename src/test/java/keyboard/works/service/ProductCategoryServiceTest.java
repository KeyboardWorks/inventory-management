package keyboard.works.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import keyboard.works.entity.ProductCategory;
import keyboard.works.model.request.ProductCategoryRequest;

@SpringBootTest
@Sql(scripts = {"/sql/product-category-delete.sql", "/sql/product-category-insert.sql"})
public class ProductCategoryServiceTest {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Test
	public void getProductCategoriesTest() {
		
		List<ProductCategory> productCategories = productCategoryService.getProductCategories();
		
		assertNotNull(productCategories);
		assertEquals(2, productCategories.size());
	}
	
	@Test
	@Sql(scripts = "/sql/product-category-delete.sql")
	public void getProductCategories_EmptyTest() {
		
		List<ProductCategory> productCategories = productCategoryService.getProductCategories();
		
		assertNotNull(productCategories);
		assertEquals(0, productCategories.size());
	}
	
	@Test
	public void getProductCategoryTest() {
		
		ProductCategory productCategory = productCategoryService.getProductCategory("001");
		
		assertNotNull(productCategory);
	}
	
	@Test
	public void getProductCategory_NotFoundTest() {
		
		assertThrows(RuntimeException.class, () -> {
			productCategoryService.getProductCategory("003");
		});
		
	}
	
	@Test
	public void createProductCategoryTest() {
		
		ProductCategoryRequest request = new ProductCategoryRequest();
		request.setCode("Product Category Code 3");
		request.setName("Product Category Name 3");
		request.setParent("001");
		
		ProductCategory productCategory = productCategoryService.createProductCategory(request);
	
		assertNotNull(productCategory);
		assertNotNull(productCategory.getId());
		assertNotNull(productCategory.getParent());
	}
	
	@Test
	public void createProductCategory_ValidationTest() {
		ProductCategoryRequest request = new ProductCategoryRequest();
		
		assertThrows(ConstraintViolationException.class, () -> {
			productCategoryService.createProductCategory(request);
		});
		
	}
	
	@Test
	public void updateProductCategoryTest() {
		
		ProductCategoryRequest request = new ProductCategoryRequest();
		request.setCode("Product Category Code 3");
		request.setName("Product Category Name 3");
		
		ProductCategory productCategory = productCategoryService.updateProductCategory("001", request);
		
		assertNotNull(productCategory);
		assertEquals("Product Category Code 3", productCategory.getCode());
		assertEquals("Product Category Name 3", productCategory.getName());
		
	}
	
	@Test
	public void updateProductCategory_NotFoundTest() {
		ProductCategoryRequest request = new ProductCategoryRequest();
		request.setCode("Product Category Code 3");
		request.setName("Product Category Name 3");
		
		assertThrows(RuntimeException.class, () -> {
			productCategoryService.updateProductCategory("003", request);
		});
	}
	
	@Test
	public void updateProductCategory_ValidationTest() {
		ProductCategoryRequest request = new ProductCategoryRequest();
		
		assertThrows(ConstraintViolationException.class, () -> {
			productCategoryService.updateProductCategory("001", request);
		});
	}
	
	@Test
	public void deleteProductCategoryTest() {
		
		productCategoryService.deleteProductCategory("001");
		
		assertThrows(RuntimeException.class, () -> {
			productCategoryService.getProductCategory("001");
		});
		
	}
	
	@Test
	public void deleteProductCategory_NotFoundTest() {
		
		assertThrows(RuntimeException.class, () -> {
			productCategoryService.getProductCategory("003");
		});
		
	}
	
}
