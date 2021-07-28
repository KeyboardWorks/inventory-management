package keyboard.works.service;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.entity.Product;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.model.request.ProductPackagingRequest;

@Validated
public interface ProductPackagingService {

	ProductPackaging getProductPackaging(String id);
	
	ProductPackaging createProductPacking(@Valid ProductPackagingRequest request);
	
	ProductPackaging updateProductPackaging(String id, @Valid ProductPackagingRequest request);
	
	ProductPackaging createDefaultProductPackaging(Product product);
	
}
