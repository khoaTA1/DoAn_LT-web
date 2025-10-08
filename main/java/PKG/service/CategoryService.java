package PKG.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import PKG.entity.Category;

public interface CategoryService {

	void deleteById(Integer id);

	long count();

	<S extends Category> boolean exists(Example<S> example);

	boolean existsById(Integer id);

	Optional<Category> findById(Integer id);

	List<Category> findAll();

	Page<Category> findAll(Pageable pageable);

	<S extends Category> S save(S entity);

	Page<Category> findByCategoryNameContaining(String name, Pageable pageable);

	List<Category> findByCategoryNameContaining(String name);

	Page<Category> findAllByUid(int uid, Pageable pageable);	
}
