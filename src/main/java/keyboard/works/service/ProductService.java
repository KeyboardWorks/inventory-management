package keyboard.works.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.entity.Product;
import keyboard.works.model.request.ProductRequest;

@Validated
public interface ProductService {

	List<Product> getProducts();
	
	Product getProduct(String id);
	
	Product createProduct(@Valid ProductRequest request);
	
	Product updateProduct(String id, @Valid ProductRequest request);
	
	void deleteProduct(String id);
	
}
