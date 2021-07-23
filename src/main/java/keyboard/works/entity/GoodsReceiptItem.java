package keyboard.works.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name = "goods_receipt_item")
public class GoodsReceiptItem extends BaseEntity {

	@Column(name = "receipted")
	private BigDecimal receipted;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_goods_receipt")
	@LazyToOne(LazyToOneOption.PROXY)
	private GoodsReceipt goodsReceipt;
	
}
