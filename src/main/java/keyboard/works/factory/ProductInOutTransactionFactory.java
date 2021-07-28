package keyboard.works.factory;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import keyboard.works.entity.InventoryTransactionItem;
import keyboard.works.entity.ProductInOutTransaction;

public class ProductInOutTransactionFactory {

	public static ProductInOutTransaction createInTransaction(InventoryTransactionItem inventoryTransactionItem) {
		BigDecimal quantityLeft = inventoryTransactionItem.getReceipted().multiply(inventoryTransactionItem.getProductPackaging().getQuantityToBase());
		return createInTransaction(inventoryTransactionItem, quantityLeft);
	}
	
	public static ProductInOutTransaction createInAverangeTransaction(InventoryTransactionItem inventoryTransactionItem) {
		return createInTransaction(inventoryTransactionItem, BigDecimal.ZERO);
	}
	
	private static ProductInOutTransaction createInTransaction(InventoryTransactionItem inventoryTransactionItem, BigDecimal quantityLeft) {
		return createTransaction(inventoryTransactionItem, inventoryTransactionItem.getReceipted(), quantityLeft);
	}
	
	private static ProductInOutTransaction createTransaction(InventoryTransactionItem inventoryTransactionItem, BigDecimal quantity, BigDecimal quantityLeft) {
		ProductInOutTransaction productInOutTransaction = new ProductInOutTransaction();
		BeanUtils.copyProperties(inventoryTransactionItem, productInOutTransaction);
		
		productInOutTransaction.setQuantity(quantity);
		productInOutTransaction.setQuantityLeft(quantityLeft);
		productInOutTransaction.setInventoryTransactionItem(inventoryTransactionItem);
		
		return productInOutTransaction;
	}
	
}
