package keyboard.works.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.ProductInOutTransaction;

@Repository
public interface ProductInOutTransactionRepository extends JpaRepository<ProductInOutTransaction, String> {

	@Query("SELECT inOut FROM ProductInOutTransaction inOut "
			+ "WHERE inOut.type = 'IN' AND inOut.quantityLeft > 0 "
			+ "AND inOut.product.id =:product "
			+ "AND inOut.productPackaging.id =:productPackaging")
	List<ProductInOutTransaction> findAvailableStock(@Param("product")String productId, @Param("productPackaging")String productPackagingId, Sort sort);
	
}
