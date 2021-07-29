package keyboard.works.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import keyboard.works.entity.GoodsIssue;
import keyboard.works.entity.GoodsIssueItem;
import keyboard.works.entity.Product;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.model.request.GoodsIssueItemRequest;
import keyboard.works.model.request.GoodsIssueRequest;
import keyboard.works.repository.GoodsIssueItemRepository;
import keyboard.works.repository.GoodsIssueRepository;
import keyboard.works.service.GoodsIssueService;
import keyboard.works.service.ProductPackagingService;
import keyboard.works.service.ProductService;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsIssueServiceImpl implements GoodsIssueService {

	@Autowired
	private GoodsIssueRepository goodsIssueRepository;
	
	@Autowired
	private GoodsIssueItemRepository goodsIssueItemRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductPackagingService productPackagingService;
	
	@Override
	public GoodsIssue createGoodsIssue(@Valid GoodsIssueRequest request) {
		
		GoodsIssue goodsIssue = new GoodsIssue();
		goodsIssue.setItems(new HashSet<>());
		BeanUtils.copyProperties(request, goodsIssue);
		
		Set<GoodsIssueItem> items = request.getItems().stream()
				.map(requestItem -> {
					return createGoodsIssueItem(requestItem, goodsIssue);
				})
				.collect(Collectors.toSet());
		
		goodsIssue.getItems().addAll(items);
		
		GoodsIssue storedGoodsIssue = goodsIssueRepository.save(goodsIssue);
		
		return storedGoodsIssue;
	}
	
	@Override
	public GoodsIssue updateGoodsIssue(String id, @Valid GoodsIssueRequest request) {
		
		GoodsIssue goodsIssue = loadGoodsIssue(id);
		BeanUtils.copyProperties(request, goodsIssue);
		
		request.getItems().forEach(requestItem -> {
		
			GoodsIssueItem goodsIssueItem = loadGoodsIssueItem(requestItem.getId());
			BeanUtils.copyProperties(requestItem, goodsIssueItem);
			
			Product product = productService.getProduct(requestItem.getProduct());
			ProductPackaging productPackaging = productPackagingService.getProductPackaging(requestItem.getProductPackaging());
			
			goodsIssueItem.setProduct(product);
			goodsIssueItem.setProductPackaging(productPackaging);
			
			goodsIssueItemRepository.save(goodsIssueItem);
			
		});
		
		GoodsIssue updatedGoodsIssue = goodsIssueRepository.save(goodsIssue);
		
		return updatedGoodsIssue;
	}

	@Override
	public List<GoodsIssue> getGoodsIssues() {
		return goodsIssueRepository.findAll();
	}

	@Override
	public GoodsIssue getGoodsIssue(String id) {
		
		GoodsIssue goodsIssue = loadGoodsIssue(id);
		
		return goodsIssue;
	}

	@Override
	public void deleteGoodsIssue(String id) {
		
		GoodsIssue goodsIssue = loadGoodsIssue(id);
		
		goodsIssueRepository.delete(goodsIssue);
		
	}

	private GoodsIssue loadGoodsIssue(String id) {
		
		GoodsIssue goodsIssue = goodsIssueRepository.findById(id).orElseThrow(() -> {
			return new EntityNotFoundException("Goods Issue not found!");
		});
	
		return goodsIssue;
	}
	
	private GoodsIssueItem loadGoodsIssueItem(String id) {
		
		GoodsIssueItem goodsIssueItem = goodsIssueItemRepository.findById(id).orElseThrow(() -> {
			return new EntityNotFoundException("Goods Issue Item not found!");
		});
	
		return goodsIssueItem;
	}
	
	private GoodsIssueItem createGoodsIssueItem(GoodsIssueItemRequest requestItem, GoodsIssue goodsIssue) {
		
		GoodsIssueItem goodsIssueItem = new GoodsIssueItem();
		BeanUtils.copyProperties(requestItem, goodsIssueItem);
		
		Product product = productService.getProduct(requestItem.getProduct());
		ProductPackaging productPackaging = productPackagingService.getProductPackaging(requestItem.getProductPackaging());
		
		goodsIssueItem.setProduct(product);
		goodsIssueItem.setProductPackaging(productPackaging);
		goodsIssueItem.setGoodsIssue(goodsIssue);
		
		return goodsIssueItem;
	}
	
}
