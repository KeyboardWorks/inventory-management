package keyboard.works.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.Product;
import keyboard.works.entity.ProductAveragePrice;
import keyboard.works.entity.ProductPackaging;

@Repository
public interface ProductAveragePriceRepository extends JpaRepository<ProductAveragePrice, String> {

	@Lock(LockModeType.PESSIMISTIC_READ)
	Optional<ProductAveragePrice> findByProduct_IdAndProductPackaging_Id(String productId, String productPackagingId);
	
	Optional<ProductAveragePrice> findByProductAndProductPackaging(Product product, ProductPackaging productPackaging);
	
}
