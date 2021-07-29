package keyboard.works.factory;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import keyboard.works.entity.InventoryTransactionInItem;
import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.ProductInOutTransaction;

public class ProductInOutTransactionFactory {

	public static <T extends InventoryTransactionItem & InventoryTransactionInItem> ProductInOutTransaction createInTransaction(T inventoryTransactionItem) {
		BigDecimal quantityLeft = inventoryTransactionItem.getReceipted().multiply(inventoryTransactionItem.getProductPackaging().getQuantityToBase());
		return createInTransaction(inventoryTransactionItem, quantityLeft);
	}
	
	public static <T extends InventoryTransactionItem & InventoryTransactionInItem> ProductInOutTransaction createInAverageTransaction(T inventoryTransactionItem) {
		return createInTransaction(inventoryTransactionItem, BigDecimal.ZERO);
	}
	
	private static <T extends InventoryTransactionItem & InventoryTransactionInItem> ProductInOutTransaction createInTransaction(T inventoryTransactionItem, BigDecimal quantityLeft) {
		return createInTransaction(inventoryTransactionItem, inventoryTransactionItem.getReceipted(), quantityLeft);
	}
	
	private static <T extends InventoryTransactionItem & InventoryTransactionInItem> ProductInOutTransaction createInTransaction(T inventoryTransactionItem, BigDecimal quantity, BigDecimal quantityLeft) {
		ProductInOutTransaction productInOutTransaction = new ProductInOutTransaction();
		BeanUtils.copyProperties(inventoryTransactionItem, productInOutTransaction);
		
		productInOutTransaction.setQuantity(quantity);
		productInOutTransaction.setQuantityLeft(quantityLeft);
		productInOutTransaction.setInventoryTransactionItem(inventoryTransactionItem);
		
		return productInOutTransaction;
	}
	
}
