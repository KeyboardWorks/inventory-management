package keyboard.works.service;

import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.InventoryTransactionOutItem;

public interface InventoryOutFifoLifoService {

	<T extends InventoryTransactionItem & InventoryTransactionOutItem> void executeFifo(T inventoryTransactionItem);
	
	<T extends InventoryTransactionItem & InventoryTransactionOutItem> void executeLifo(T inventoryTransactionItem);
	
}
