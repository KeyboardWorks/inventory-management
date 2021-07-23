package keyboard.works.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import keyboard.works.entity.converter.BooleanToStringConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "product_category")
public class ProductCategory extends BaseEntity {

	@Column(name = "code", nullable = false, unique = true)
	private String code;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "active")
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean active;
	
	@Lob
	@Column(name = "note")
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product_category_parent")
	@LazyToOne(LazyToOneOption.PROXY)
	private ProductCategory parent;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<ProductCategory> childs;
	
}
