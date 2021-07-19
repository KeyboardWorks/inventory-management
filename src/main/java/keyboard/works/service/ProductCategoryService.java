package keyboard.works.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.model.request.ProductCategoryRequest;
import keyboard.works.model.response.ProductCategoryResponse;

@Validated
public interface ProductCategoryService {

	List<ProductCategoryResponse> getProductCategories();
	
	ProductCategoryResponse getProductCategory(String id);
	
	ProductCategoryResponse createProductCategory(@Valid ProductCategoryRequest request);
	
	ProductCategoryResponse updateProductCategory(String id, @Valid ProductCategoryRequest request);
	
	void deleteProductCategory(String id);
	
}
