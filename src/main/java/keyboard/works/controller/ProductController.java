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
import keyboard.works.model.request.ProductPackagingRequest;
import keyboard.works.model.request.ProductRequest;
import keyboard.works.model.response.ProductPackagingResponse;
import keyboard.works.model.response.ProductResponse;
import keyboard.works.service.ProductPackagingService;
import keyboard.works.service.ProductService;
import keyboard.works.utils.GenericResponseHelper;
import keyboard.works.utils.ResponseHelper;

@RestController
@RequestMapping(
	path = "/products",
	produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductPackagingService productPackagingService;
	
	@GetMapping
	public GenericResponse<List<ProductResponse>> getProducts() {
		
		List<ProductResponse> responses = ResponseHelper.createResponses(ProductResponse.class, productService.getProducts());
		
		return GenericResponseHelper.ok(responses);
	}
	
	@GetMapping(path = "{id}")
	public GenericResponse<ProductResponse> getProduct(@PathVariable("id") String id){
		
		ProductResponse response = ResponseHelper.createResponse(ProductResponse.class, productService.getProduct(id));
		
		return GenericResponseHelper.ok(response);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductResponse> createProduct(@RequestBody ProductRequest request){
		
		ProductResponse response = ResponseHelper.createResponse(ProductResponse.class, productService.createProduct(request));
		
		return GenericResponseHelper.ok(response);
	}
	
	@PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductResponse> updateProduct(@PathVariable("id")String id, @RequestBody ProductRequest request){
		
		ProductResponse response = ResponseHelper.createResponse(ProductResponse.class, productService.updateProduct(id, request));
		
		return GenericResponseHelper.ok(response);
	}
	
	@DeleteMapping(path = "{id}")
	public GenericResponse<?> deleteProduct(@PathVariable("id")String id) {
		
		productService.deleteProduct(id);
		
		return GenericResponseHelper.ok();
	}
	
	@PostMapping(path = "{productId}/packagings", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductPackagingResponse> createProductPackaging(@PathVariable("productId") String productId, @RequestBody ProductPackagingRequest request) {
		
		request.setProduct(productId);
		
		ProductPackagingResponse productPackagingResponse = ResponseHelper.createResponse(ProductPackagingResponse.class, productPackagingService.createProductPacking(request));
		
		return GenericResponseHelper.ok(productPackagingResponse);
	}
	
	@PutMapping(path = "{productId}/packagings/{productPackagingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductPackagingResponse> updateProductPackaging(@PathVariable("productId") String productId, @PathVariable("productPackagingId") String productPackagingId,
			@RequestBody ProductPackagingRequest request) {
		
		request.setProduct(productId);
		
		ProductPackagingResponse productPackagingResponse = ResponseHelper.createResponse(ProductPackagingResponse.class, productPackagingService.updateProductPackaging(productPackagingId, request));
		
		return GenericResponseHelper.ok(productPackagingResponse);
		
	}
	
}
