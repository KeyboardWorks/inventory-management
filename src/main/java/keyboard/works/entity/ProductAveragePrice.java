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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "product_average_price")
public class ProductAveragePrice extends BaseEntity {

	@Column(name = "quantity")
	private BigDecimal quantity;
	
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
	
	public BigDecimal getTotalPrice() {
		return quantity.multiply(price);
	}
	
}
