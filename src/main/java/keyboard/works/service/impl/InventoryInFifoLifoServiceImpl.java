package keyboard.works.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.InventoryTransactionInItem;
import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.ProductInOutTransaction;
import keyboard.works.factory.ProductInOutTransactionFactory;
import keyboard.works.repository.ProductInOutTransactionRepository;
import keyboard.works.service.InventoryInService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryInFifoLifoServiceImpl implements InventoryInService {

	@Autowired
	private ProductInOutTransactionRepository productInOutTransactionRepository;
	
	@Override
	public boolean isSupport(InventoryMethodType inventoryMethod) {
		return inventoryMethod.equals(InventoryMethodType.FIFO) || inventoryMethod.equals(InventoryMethodType.LIFO);
	}

	@Override
	public <T extends InventoryTransactionItem & InventoryTransactionInItem> void execute(T inventoryTransactionItem) {
		ProductInOutTransaction productInOutTransaction = ProductInOutTransactionFactory.createInTransaction(inventoryTransactionItem);
		productInOutTransactionRepository.save(productInOutTransaction);			
	}


}
