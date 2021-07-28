package keyboard.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.GoodsReceiptItem;

@Repository
public interface GoodsReceiptItemRepository extends JpaRepository<GoodsReceiptItem, String> {

}
