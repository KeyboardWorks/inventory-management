package keyboard.works.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "product_in_out_transaction")
public class ProductInOutTransaction extends BaseEntity {

	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "quantity")
	private BigDecimal quantity;
	
	//Convert Into Base Quantity
	@Column(name = "quantity_left")
	private BigDecimal quantityLeft;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product_packaging")
	@LazyToOne(LazyToOneOption.PROXY)
	private ProductPackaging productPackaging;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_inventory_transaction_item")
	@LazyToOne(LazyToOneOption.PROXY)
	private InventoryTransactionItem inventoryTransactionItem;
	
	public BigDecimal getTotalPrice() {
		return quantity.multiply(price);
	}
	
}
