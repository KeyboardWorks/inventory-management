package keyboard.works.service.impl;

import java.util.HashSet;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import keyboard.works.entity.InventoryMethod;
import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.ProductInOutTransaction;
import keyboard.works.service.InventoryInService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryInFifoLifoServiceImpl implements InventoryInService {

	@Override
	public boolean isSupport(InventoryMethod inventoryMethod) {
		return inventoryMethod.equals(InventoryMethod.FIFO) || inventoryMethod.equals(InventoryMethod.LIFO);
	}

	@Override
	public void execute(InventoryTransactionItem inventoryTransactionItem) {
		
		ProductInOutTransaction productInOutTransaction = new ProductInOutTransaction();
		BeanUtils.copyProperties(inventoryTransactionItem, productInOutTransaction);
		
		productInOutTransaction.setQuantity(inventoryTransactionItem.getReceipted());
		productInOutTransaction.setQuantityLeft(inventoryTransactionItem.getReceipted()
				.multiply(inventoryTransactionItem.getProductPackaging().getQuantityToBase()));
		productInOutTransaction.setInventoryTransactionItem(inventoryTransactionItem);
		
		inventoryTransactionItem.setInOutTransactions(new HashSet<>());
		inventoryTransactionItem.getInOutTransactions().add(productInOutTransaction);
		
	}

}
