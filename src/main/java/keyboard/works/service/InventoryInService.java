package keyboard.works.service;

import keyboard.works.entity.InventoryMethod;
import keyboard.works.entity.InventoryTransactionItem;

public interface InventoryInService {

	boolean isSupport(InventoryMethod inventoryMethod);
	
	void execute(InventoryTransactionItem inventoryTransactionItem);
	
}
