package keyboard.works.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.ProductPackaging;

@Repository
public interface ProductPackagingRepository extends JpaRepository<ProductPackaging, String> {

	Optional<ProductPackaging> findByIdAndProduct_Id(String id, String productId);
	
}
