package keyboard.works.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import keyboard.works.SpringAppContext;
import keyboard.works.entity.GoodsReceipt;
import keyboard.works.entity.GoodsReceiptItem;
import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.Product;
import keyboard.works.entity.ProductPackaging;
import keyboard.works.model.request.GoodsReceiptItemRequest;
import keyboard.works.model.request.GoodsReceiptRequest;
import keyboard.works.repository.GoodsReceiptItemRepository;
import keyboard.works.repository.GoodsReceiptRepository;
import keyboard.works.service.GoodsReceiptService;
import keyboard.works.service.InventoryInService;
import keyboard.works.service.ProductPackagingService;
import keyboard.works.service.ProductService;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsReceiptServiceImpl implements GoodsReceiptService {

	@Autowired
	private GoodsReceiptRepository goodsReceiptRepository;
	
	@Autowired
	private GoodsReceiptItemRepository goodsReceiptItemRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductPackagingService productPackagingService;
	
	@Override
	public GoodsReceipt createGoodsReceipt(GoodsReceiptRequest request) {
		
		GoodsReceipt goodsReceipt = new GoodsReceipt();
		goodsReceipt.setItems(new HashSet<>());
		BeanUtils.copyProperties(request, goodsReceipt);

		Set<GoodsReceiptItem> items = request.getItems().stream()
			.map(requestItem -> {
				return createGoodsReceiptItem(requestItem, goodsReceipt);
			})
			.collect(Collectors.toSet());
		
		goodsReceipt.getItems().addAll(items);
		
		GoodsReceipt storedGoodsReceipt = goodsReceiptRepository.save(goodsReceipt);
		storedGoodsReceipt.getItems()
			.forEach(this::doInventoryIn);
		
		return storedGoodsReceipt;
	}

	@Override
	public GoodsReceipt updateGoodsReceipt(String id, GoodsReceiptRequest request) {
		
		GoodsReceipt goodsReceipt = loadGoodsReceipt(id);
		BeanUtils.copyProperties(request, goodsReceipt);
		
		request.getItems().forEach(requestItem -> {
			
			GoodsReceiptItem goodsReceiptItem = loadGoodsReceiptItem(requestItem.getId());
			BeanUtils.copyProperties(requestItem, goodsReceiptItem);
			
			Product product = productService.getProduct(requestItem.getProduct());
			ProductPackaging productPackaging = productPackagingService.getProductPackaging(requestItem.getProductPackaging());
			
			goodsReceiptItem.setProduct(product);
			goodsReceiptItem.setProductPackaging(productPackaging);
			
			goodsReceiptItemRepository.save(goodsReceiptItem);
			
		});
		
		GoodsReceipt udpatedGoodsReceipt = goodsReceiptRepository.save(goodsReceipt);
		
		return udpatedGoodsReceipt;
	}

	@Override
	public List<GoodsReceipt> getGoodsReceipts() {
		return goodsReceiptRepository.findAll();
	}

	@Override
	public GoodsReceipt getGoodsReceipt(String id) {
		
		GoodsReceipt goodsReceipt = loadGoodsReceipt(id);
		
		return goodsReceipt;
	}

	@Override
	public void deleteGoodsReceipt(String id) {
		
		GoodsReceipt goodsReceipt = loadGoodsReceipt(id);
		
		goodsReceiptRepository.delete(goodsReceipt);
	}
	
	private GoodsReceipt loadGoodsReceipt(String id) {
		
		GoodsReceipt goodsReceipt = goodsReceiptRepository.findById(id).orElseThrow(() -> {
			return new EntityNotFoundException("Goods Receipt not found!");
		});
		
		return goodsReceipt;
	}
	
	private GoodsReceiptItem loadGoodsReceiptItem(String id) {
		
		GoodsReceiptItem goodsReceiptItem = goodsReceiptItemRepository.findById(id).orElseThrow(() -> {
			return new EntityNotFoundException("Goods Receipt Item not found!");
		});
		
		return goodsReceiptItem;
	}
	
	private GoodsReceiptItem createGoodsReceiptItem(GoodsReceiptItemRequest requestItem, GoodsReceipt goodsReceipt) {
		
		GoodsReceiptItem goodsReceiptItem = new GoodsReceiptItem();
		BeanUtils.copyProperties(requestItem, goodsReceiptItem);
		
		Product product = productService.getProduct(requestItem.getProduct());
		ProductPackaging productPackaging = productPackagingService.getProductPackaging(requestItem.getProductPackaging());
		
		goodsReceiptItem.setProduct(product);
		goodsReceiptItem.setProductPackaging(productPackaging);
		goodsReceiptItem.setGoodsReceipt(goodsReceipt);
		
		return goodsReceiptItem;
	}
	
	private void doInventoryIn(GoodsReceiptItem goodsReceiptItem) {
		SpringAppContext.getContext().getBeansOfType(InventoryInService.class).values().stream()
			.filter(inService -> {
				return inService.isSupport(InventoryMethodType.AVERAGE);
			})
			.findFirst()
			.ifPresentOrElse(inService -> {
				inService.execute(goodsReceiptItem);
			}, () -> {
				throw new RuntimeException("Inventory In method Average not support !");
			});
	}

}
