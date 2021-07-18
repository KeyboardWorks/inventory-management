package keyboard.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import keyboard.works.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

}
