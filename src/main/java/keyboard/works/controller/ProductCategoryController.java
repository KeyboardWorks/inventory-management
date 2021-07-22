package keyboard.works.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import keyboard.works.model.GenericResponse;
import keyboard.works.model.request.ProductCategoryRequest;
import keyboard.works.model.response.ProductCategoryResponse;
import keyboard.works.service.ProductCategoryService;
import keyboard.works.utils.GenericResponseHelper;
import keyboard.works.utils.ResponseHelper;

@RestController
@RequestMapping(
	path = "/product-categories",
	produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@GetMapping
	public GenericResponse<List<ProductCategoryResponse>> getProductCategories() {
		
		List<ProductCategoryResponse> responses = ResponseHelper.createResponses(ProductCategoryResponse.class, productCategoryService.getProductCategories());
		
		return GenericResponseHelper.ok(responses);
	}
	
	@GetMapping(path = "{id}")
	public GenericResponse<ProductCategoryResponse> getProductCategory(@PathVariable("id") String id) {
		
		ProductCategoryResponse response = ResponseHelper.createResponse(ProductCategoryResponse.class, productCategoryService.getProductCategory(id));
		
		return GenericResponseHelper.ok(response);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductCategoryResponse> createProductCategory(@RequestBody ProductCategoryRequest request) {
		
		ProductCategoryResponse response = ResponseHelper.createResponse(ProductCategoryResponse.class, productCategoryService.createProductCategory(request));
		
		return GenericResponseHelper.ok(response);
	}
	
	@PutMapping(
		path = "{id}",
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public GenericResponse<ProductCategoryResponse> updateProductCategory(@PathVariable("id") String id, @RequestBody ProductCategoryRequest request) {
		
		ProductCategoryResponse response = ResponseHelper.createResponse(ProductCategoryResponse.class, productCategoryService.updateProductCategory(id, request));
		
		return GenericResponseHelper.ok(response);
	}
	
	@DeleteMapping(path = "{id}")
	public GenericResponse<?> deleteProductCategory(@PathVariable("id") String id) {
		
		productCategoryService.deleteProductCategory(id);
		
		return GenericResponseHelper.ok();
	}
	
}
