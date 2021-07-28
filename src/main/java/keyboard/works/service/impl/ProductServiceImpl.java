package keyboard.works.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import keyboard.works.entity.Product;
import keyboard.works.entity.ProductCategory;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.model.request.ProductRequest;
import keyboard.works.repository.ProductRepository;
import keyboard.works.service.ProductCategoryService;
import keyboard.works.service.ProductPackagingService;
import keyboard.works.service.ProductService;

@Service
@Transactional(rollbackOn = Exception.class)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductPackagingService productPackagingService;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Override
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(String id) {
		Product product = loadProduct(id);
		
		return product;
	}

	@Override
	public Product createProduct(ProductRequest request) {
		
		Product product = new Product();
		product.setPackagings(new HashSet<>());
		BeanUtils.copyProperties(request, product);
		
		ProductCategory productCategory = productCategoryService.getProductCategory(request.getProductCategory());
		product.setProductCategory(productCategory);
		
		ProductPackaging productPackaging = productPackagingService.createDefaultProductPackaging(product);
		product.getPackagings().add(productPackaging);
		
		product = productRepository.save(product);
		
		return product;
	}

	@Override
	public Product updateProduct(String id, ProductRequest request) {
		
		Product product = loadProduct(id);
		BeanUtils.copyProperties(request, product);
		
		product = productRepository.save(product);
		
		return product;
	}

	@Override
	public void deleteProduct(String id) {

		Product product = loadProduct(id);
		
		productRepository.delete(product);
	}
	
	private Product loadProduct(String id) {
		
		Product product = productRepository.findById(id).orElseThrow(() -> {
			throw new EntityNotFoundException("Product not found !");
		});
		
		return product;
	}

}
