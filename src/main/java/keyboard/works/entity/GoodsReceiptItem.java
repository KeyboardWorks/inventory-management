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
@Table(name = "goods_receipt_item")
public class GoodsReceiptItem extends InventoryTransactionItem implements InventoryTransactionInItem {

	@Column(name = "price")
	private BigDecimal price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_goods_receipt")
	@LazyToOne(LazyToOneOption.PROXY)
	private GoodsReceipt goodsReceipt;

	@Override
	public InventoryType getType() {
		return InventoryType.IN;
	}
	
	@Override
	public LocalDate getDate() {
		return getGoodsReceipt().getDate();
	}

}
