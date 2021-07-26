package keyboard.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.GoodsReceipt;

@Repository
public interface GoodsReceiptRepository extends JpaRepository<GoodsReceipt, String> {

}
