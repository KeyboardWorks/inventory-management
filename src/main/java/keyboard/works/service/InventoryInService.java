package keyboard.works.service;

import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.InventoryTransactionInItem;
import keyboard.works.entity.InventoryTransactionItem;

public interface InventoryInService {

	boolean isSupport(InventoryMethodType inventoryMethod);
	
	<T extends InventoryTransactionItem & InventoryTransactionInItem> void execute(T inventoryTransactionItem);
	
}
