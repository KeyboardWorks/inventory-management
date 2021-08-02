package keyboard.works.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.InventoryTransactionOutItem;
import keyboard.works.entity.ProductInOutTransaction;
import keyboard.works.repository.ProductInOutTransactionRepository;
import keyboard.works.service.InventoryOutFifoLifoService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryOutFifoLifoServiceImpl implements InventoryOutFifoLifoService {

	@Autowired
	private ProductInOutTransactionRepository productInOutTransactionRepository;
	
	@Override
	public <T extends InventoryTransactionItem & InventoryTransactionOutItem> void executeFifo(T inventoryTransactionItem) {
		
		BigDecimal issued = inventoryTransactionItem.getIssued();
		
		checkStock(inventoryTransactionItem, issued);
		
		execute(inventoryTransactionItem, issued, getFifoIns(inventoryTransactionItem));
		
	}

	@Override
	public <T extends InventoryTransactionItem & InventoryTransactionOutItem> void executeLifo(T inventoryTransactionItem) {

		BigDecimal issued = inventoryTransactionItem.getIssued();
		
		checkStock(inventoryTransactionItem, issued);
		
		execute(inventoryTransactionItem, issued, getLifoIns(inventoryTransactionItem));
		
	}
	
	private <T extends InventoryTransactionItem & InventoryTransactionOutItem> void checkStock(T inventoryTransactionItem, BigDecimal issued) {
		
		BigDecimal availableStock = productInOutTransactionRepository.getAvailableStock(inventoryTransactionItem.getProduct().getId(), 
				inventoryTransactionItem.getProductPackaging().getId());
		
		if(issued.compareTo(availableStock) > 0)
			throw new RuntimeException("Product " + inventoryTransactionItem.getProduct().getName() + 
					", Packaging " + inventoryTransactionItem.getProductPackaging().getUnitOfMeasure().getName() + " " +
					inventoryTransactionItem.getProductPackaging().getQuantityToBase() + " insufficient stock!");
	}
	
	private <T extends InventoryTransactionItem & InventoryTransactionOutItem> List<ProductInOutTransaction> getFifoIns(T inventoryTransactionItem) {
		List<ProductInOutTransaction> ins = productInOutTransactionRepository.findAvailableStock(
				inventoryTransactionItem.getProduct().getId(), inventoryTransactionItem.getProductPackaging().getId(), 
				Sort.by(Order.asc("date"), Order.asc("createdDateTime")));
		
		return ins;
	}
	
	private <T extends InventoryTransactionItem & InventoryTransactionOutItem> List<ProductInOutTransaction> getLifoIns(T inventoryTransactionItem) {
		List<ProductInOutTransaction> ins = productInOutTransactionRepository.findAvailableStock(
				inventoryTransactionItem.getProduct().getId(), inventoryTransactionItem.getProductPackaging().getId(), 
				Sort.by(Order.desc("date"), Order.desc("createdDateTime")));
		
		return ins;
	}
	
	public <T extends InventoryTransactionItem & InventoryTransactionOutItem> void execute(T inventoryTransactionItem, BigDecimal issued, List<ProductInOutTransaction> ins) {
		for(ProductInOutTransaction in : ins) {
			
			BigDecimal difference = issued.subtract(in.getQuantityLeft());
			
			ProductInOutTransaction out = createOutTransaction(inventoryTransactionItem, in, getOutQuantity(difference, in, issued));
			
			in.setQuantityLeft(getInLeftQuantity(difference, in, issued));
			issued = difference;
			
			productInOutTransactionRepository.save(out);
			productInOutTransactionRepository.save(in);
			
			if(difference.compareTo(BigDecimal.ZERO) <= 0)
				break;
		}
	}
	
	private <T extends InventoryTransactionItem & InventoryTransactionOutItem> ProductInOutTransaction createOutTransaction(T inventoryTransactionItem, 
			ProductInOutTransaction in, BigDecimal quantity) {
		
		ProductInOutTransaction out = new ProductInOutTransaction();
		BeanUtils.copyProperties(inventoryTransactionItem, out);
		out.setPrice(in.getPrice());
		out.setQuantity(quantity);
		out.setInventoryTransactionItem(inventoryTransactionItem);
		out.setInFrom(in);
		
		return out;
	}
	
	private BigDecimal getOutQuantity(BigDecimal difference, ProductInOutTransaction in, BigDecimal issued) {
		return difference.compareTo(BigDecimal.ZERO) >= 0 ? in.getQuantityLeft() : issued;
	}
	
	private BigDecimal getInLeftQuantity(BigDecimal difference, ProductInOutTransaction in, BigDecimal issued) {
		return difference.compareTo(BigDecimal.ZERO) >= 0 ? BigDecimal.ZERO : in.getQuantityLeft().subtract(issued);
	}

}
