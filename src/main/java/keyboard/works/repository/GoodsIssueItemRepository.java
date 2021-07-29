package keyboard.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.GoodsIssueItem;

@Repository
public interface GoodsIssueItemRepository extends JpaRepository<GoodsIssueItem, String> {

}
