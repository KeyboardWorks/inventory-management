package keyboard.works.service;

import keyboard.works.entity.InventoryTransactionOutItem;
import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.InventoryTransactionItem;

public interface InventoryOutService {

	boolean isSupport(InventoryMethodType inventoryMethod);
	
	<T extends InventoryTransactionItem & InventoryTransactionOutItem> void execute(T inventoryTransactionItem);
	
}
