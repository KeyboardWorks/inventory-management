package keyboard.works.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.entity.ProductCategory;
import keyboard.works.model.request.ProductCategoryRequest;

@Validated
public interface ProductCategoryService {

	List<ProductCategory> getProductCategories();
	
	ProductCategory getProductCategory(String id);
	
	ProductCategory createProductCategory(@Valid ProductCategoryRequest request);
	
	ProductCategory updateProductCategory(String id, @Valid ProductCategoryRequest request);
	
	void deleteProductCategory(String id);
	
}
