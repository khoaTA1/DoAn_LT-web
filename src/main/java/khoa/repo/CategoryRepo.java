package khoa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import khoa.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
	List<Category> findByCategoryNameContaining(String name);
	Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
}
