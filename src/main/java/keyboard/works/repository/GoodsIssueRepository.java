package keyboard.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.GoodsIssue;

@Repository
public interface GoodsIssueRepository extends JpaRepository<GoodsIssue, String> {

}
