package keyboard.works.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.InventoryType;
import keyboard.works.entity.ProductInOutTransaction;

@Repository
public interface ProductInOutTransactionRepository extends JpaRepository<ProductInOutTransaction, String> {

	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("SELECT inOut FROM ProductInOutTransaction inOut "
			+ "WHERE inOut.type = 'IN' AND inOut.quantityLeft > 0 "
			+ "AND inOut.product.id =:product "
			+ "AND inOut.productPackaging.id =:productPackaging")
	List<ProductInOutTransaction> findAvailableStock(@Param("product")String productId, @Param("productPackaging")String productPackagingId, Sort sort);
	
	@Query("SELECT COALESCE(SUM(inOut.quantityLeft), 0) FROM ProductInOutTransaction inOut "
			+ "WHERE inOut.type = 'IN' AND inOut.quantityLeft > 0 "
			+ "AND inOut.product.id =:product "
			+ "AND inOut.productPackaging.id =:productPackaging")
	BigDecimal getAvailableStock(@Param("product")String productId, @Param("productPackaging")String productPackagingId);
	
	List<ProductInOutTransaction> findByType(InventoryType type);
	
}
