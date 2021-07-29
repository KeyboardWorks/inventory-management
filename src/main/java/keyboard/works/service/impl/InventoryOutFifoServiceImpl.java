package keyboard.works.service.impl;

import org.springframework.stereotype.Service;

import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.InventoryTransactionOutItem;
import keyboard.works.service.InventoryOutService;

@Service
public class InventoryOutFifoServiceImpl implements InventoryOutService {

	@Override
	public boolean isSupport(InventoryMethodType inventoryMethod) {
		return inventoryMethod.equals(InventoryMethodType.FIFO);
	}

	@Override
	public <T extends InventoryTransactionItem & InventoryTransactionOutItem> void execute(T inventoryTransactionItem) {
		
	}

}
