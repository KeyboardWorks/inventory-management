package keyboard.works.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import keyboard.works.entity.ProductCategory;

@SpringBootTest
public class ProductCategoryServiceTest {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Test
	@Sql(scripts = {"/sql/product-category-delete.sql", "/sql/product-category-insert.sql"})
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
	@Sql(scripts = {"/sql/product-category-delete.sql", "/sql/product-category-insert.sql"})
	public void getProductCategoryTest() {
		
		ProductCategory productCategory = productCategoryService.getProductCategory("001");
		
		assertNotNull(productCategory);
	}
	
	@Test
	@Sql(scripts = {"/sql/product-category-delete.sql", "/sql/product-category-insert.sql"})
	public void getProductCategory_NotFoundTest() {
		
		assertThrows(RuntimeException.class, () -> {
			productCategoryService.getProductCategory("003");
		});
		
	}
	
}
