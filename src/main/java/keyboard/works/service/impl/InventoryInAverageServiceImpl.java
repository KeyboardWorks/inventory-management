package keyboard.works.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.ProductAveragePrice;
import keyboard.works.entity.ProductInOutTransaction;
import keyboard.works.factory.ProductInOutTransactionFactory;
import keyboard.works.repository.ProductAveragePriceRepository;
import keyboard.works.repository.ProductInOutTransactionRepository;
import keyboard.works.service.InventoryInService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryInAverageServiceImpl implements InventoryInService {

	@Autowired
	private ProductAveragePriceRepository productAveragePriceRepository;
	
	@Autowired
	private ProductInOutTransactionRepository productInOutTransactionRepository;
	
	@Override
	public boolean isSupport(InventoryMethodType inventoryMethod) {
		return inventoryMethod.equals(InventoryMethodType.AVERAGE);
	}

	@Override
	public void execute(InventoryTransactionItem inventoryTransactionItem) {
		
		ProductInOutTransaction productInOutTransaction = ProductInOutTransactionFactory.createInAverageTransaction(inventoryTransactionItem);
		productInOutTransactionRepository.save(productInOutTransaction);
		
		ProductAveragePrice averageTransaction = productAveragePriceRepository.
				findByProduct_IdAndProductPackaging_Id(inventoryTransactionItem.getProduct().getId(), inventoryTransactionItem.getProductPackaging().getId())
				.orElseGet(() -> {
					return createDefaultAveragePrice(inventoryTransactionItem);
				});
		
		BigDecimal lastPrice = averageTransaction.getTotalPrice();
		lastPrice = lastPrice.add(inventoryTransactionItem.getPrice().multiply(inventoryTransactionItem.getReceipted()));
		
		averageTransaction.setQuantity(averageTransaction.getQuantity().add(inventoryTransactionItem.getReceipted()));
		averageTransaction.setPrice(lastPrice.divide(averageTransaction.getQuantity(), 2, RoundingMode.HALF_UP));
		
		productAveragePriceRepository.save(averageTransaction);
	}
	
	private ProductAveragePrice createDefaultAveragePrice(InventoryTransactionItem inventoryTransactionItem) {
	
		ProductAveragePrice averageTransaction = new ProductAveragePrice();
		BeanUtils.copyProperties(inventoryTransactionItem, averageTransaction, "price");
		averageTransaction.setPrice(BigDecimal.ZERO);
		averageTransaction.setQuantity(BigDecimal.ZERO);
		
		return averageTransaction;
	}

}
