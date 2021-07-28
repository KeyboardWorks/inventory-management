package keyboard.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.ProductInOutTransaction;

@Repository
public interface ProductInOutTransactionRepository extends JpaRepository<ProductInOutTransaction, String> {

}
