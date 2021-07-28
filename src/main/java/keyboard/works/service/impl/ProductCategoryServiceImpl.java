package keyboard.works.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import keyboard.works.entity.ProductCategory;
import keyboard.works.model.request.ProductCategoryRequest;
import keyboard.works.repository.ProductCategoryRepository;
import keyboard.works.service.ProductCategoryService;

@Service
@Transactional(rollbackOn = Exception.class)
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Override
	public List<ProductCategory> getProductCategories() {
		return productCategoryRepository.findAll();
	}

	@Override
	public ProductCategory getProductCategory(String id) {
		
		ProductCategory productCategory = loadProductCategory(id);
		
		return productCategory;
	}

	@Override
	public ProductCategory createProductCategory(ProductCategoryRequest request) {
		
		ProductCategory productCategory = new ProductCategory();
		BeanUtils.copyProperties(request, productCategory);
		
		setProductCategoryParent(request, productCategory);
		
		productCategory = productCategoryRepository.save(productCategory);
		
		return productCategory;
	}

	@Override
	public ProductCategory updateProductCategory(String id, ProductCategoryRequest request) {
		
		ProductCategory productCategory = loadProductCategory(id);
		
		BeanUtils.copyProperties(request, productCategory);

		setProductCategoryParent(request, productCategory);
		
		productCategory = productCategoryRepository.save(productCategory);
		
		return productCategory;
	}

	@Override
	public void deleteProductCategory(String id) {
		ProductCategory productCategory = loadProductCategory(id);
		
		productCategoryRepository.delete(productCategory);
	}

	private ProductCategory loadProductCategory(String id) {
		return loadProductCategory(id, "Product category not found !");
	}
	
	private ProductCategory loadProductCategory(String id, String message) {
		ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(() -> {
			throw new EntityNotFoundException(message);
		});
		
		return productCategory;
	}
	
	private void setProductCategoryParent(ProductCategoryRequest request, ProductCategory productCategory) {
		
		if(request.getParent() != null && !request.getParent().isBlank())
		{
			ProductCategory parent = loadProductCategory(request.getParent(), "Product category parent not found !");
			productCategory.setParent(parent);
		}
		
	}
	
}
