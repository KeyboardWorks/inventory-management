package keyboard.works.service.impl;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import keyboard.works.entity.Product;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.entity.UnitOfMeasure;
import keyboard.works.model.request.ProductPackagingRequest;
import keyboard.works.repository.ProductPackagingRepository;
import keyboard.works.repository.ProductRepository;
import keyboard.works.service.ProductPackagingService;
import keyboard.works.service.UnitOfMeasureService;

@Service
@Transactional(rollbackOn = Exception.class)
public class ProductPackagingServiceImpl implements ProductPackagingService {

	@Autowired
	private ProductPackagingRepository productPackagingRepository;
	
	@Autowired
	private UnitOfMeasureService unitOfMeasureService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public ProductPackaging createProductPacking(ProductPackagingRequest request) {
		
		ProductPackaging productPackaging = new ProductPackaging();
		BeanUtils.copyProperties(request, productPackaging);
		
		UnitOfMeasure unitOfMeasure = unitOfMeasureService.getUnitOfMeasure(request.getUnitOfMeasure());
		Product product = loadProduct(request.getProduct());
		
		productPackaging.setUnitOfMeasure(unitOfMeasure);
		productPackaging.setProduct(product);
		
		productPackaging = productPackagingRepository.save(productPackaging);
		
		return productPackaging;
	}

	@Override
	public ProductPackaging updateProductPackaging(String id, ProductPackagingRequest request) {
		
		ProductPackaging productPackaging = productPackagingRepository.findByIdAndProduct_Id(id, request.getProduct())
			.orElseThrow(() -> {
				return new EntityNotFoundException("Product Packaging not found");
			});
		
		BeanUtils.copyProperties(request, productPackaging);
		
		UnitOfMeasure unitOfMeasure = unitOfMeasureService.getUnitOfMeasure(request.getUnitOfMeasure());
		productPackaging.setUnitOfMeasure(unitOfMeasure);
		
		productPackaging = productPackagingRepository.save(productPackaging);
		
		return productPackaging;
	}
	
	@Override
	public ProductPackaging createDefaultProductPackaging(Product product) {
		
		UnitOfMeasure unitOfMeasure = unitOfMeasureService.getDefaultUnitOfMeasure();
		
		ProductPackaging productPackaging = new ProductPackaging();
		productPackaging.setQuantityToBase(BigDecimal.ONE);
		productPackaging.setUnitOfMeasure(unitOfMeasure);
		productPackaging.setProduct(product);
		
		return productPackaging;
	}
	
	private Product loadProduct(String id) {
		
		Product product = productRepository.findById(id).orElseThrow(() -> {
			return new EntityNotFoundException("Product not found !");
		});
		
		return product;
	}

	@Override
	public ProductPackaging getProductPackaging(String id) {
		
		ProductPackaging productPackaging = loadProductPackaging(id);
		
		return productPackaging;
	}
	
	private ProductPackaging loadProductPackaging(String id) {
		
		ProductPackaging productPackaging = productPackagingRepository.findById(id).orElseThrow(() -> {
			return new EntityNotFoundException("Product Packaging not found !");
		});
		
		return productPackaging;
	}
	
}
