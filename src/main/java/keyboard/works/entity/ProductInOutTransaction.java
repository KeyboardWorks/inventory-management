package keyboard.works.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private InventoryType type;
	
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
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product_in_out_transaction_in_from")
	@LazyToOne(LazyToOneOption.PROXY)
	private ProductInOutTransaction inFrom;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "inFrom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@OrderBy("createdDateTime")
	private Set<ProductInOutTransaction> outTos;
	
	public BigDecimal getTotalPrice() {
		return quantity.multiply(price);
	}
	
}
