package keyboard.works.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import keyboard.works.entity.InventoryMethodType;
import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.InventoryTransactionOutItem;
import keyboard.works.service.InventoryOutFifoLifoService;
import keyboard.works.service.InventoryOutService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryOutLifoServiceImpl implements InventoryOutService {

	@Autowired
	private InventoryOutFifoLifoService inventoryOutFifoLifoService;
	
	@Override
	public boolean isSupport(InventoryMethodType inventoryMethod) {
		return inventoryMethod.equals(InventoryMethodType.LIFO);
	}

	@Override
	public <T extends InventoryTransactionItem & InventoryTransactionOutItem> void execute(T inventoryTransactionItem) {

		inventoryOutFifoLifoService.executeLifo(inventoryTransactionItem);
		
	}

}
