package keyboard.works.entity;

import java.time.LocalDate;

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
@Table(name = "goods_issue_item")
public class GoodsIssueItem extends InventoryTransactionItem implements InventoryTransactionOutItem {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_goods_issue")
	@LazyToOne(LazyToOneOption.PROXY)
	private GoodsIssue goodsIssue;
	
	@Override
	public LocalDate getDate() {
		return getGoodsIssue().getDate();
	}

	@Override
	public InventoryType getType() {
		return InventoryType.OUT;
	}

}
