package keyboard.works.service;

import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.InventoryTransactionItem;

public interface InventoryInService {

	boolean isSupport(InventoryMethodType inventoryMethod);
	
	void execute(InventoryTransactionItem inventoryTransactionItem);
	
}
