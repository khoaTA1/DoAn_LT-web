 package khoa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import khoa.entity.Category;
import khoa.repo.CategoryRepo;
import khoa.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepo caterepo;
	

	@Override
	public List<Category> findByCategoryNameContaining(String name) {
		return caterepo.findByCategoryNameContaining(name);
	}

	@Override
	public Page<Category> findByCategoryNameContaining(String name, Pageable pageable) {
		return caterepo.findByCategoryNameContaining(name, pageable);
	}

	@Override
	public <S extends Category> S save(S entity) {
		return caterepo.save(entity);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return caterepo.findAll(pageable);
	}

	@Override
	public List<Category> findAll() {
		return caterepo.findAll();
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return caterepo.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return caterepo.existsById(id);
	}

	@Override
	public <S extends Category> boolean exists(Example<S> example) {
		return caterepo.exists(example);
	}

	@Override
	public long count() {
		return caterepo.count();
	}

	@Override
	public void deleteById(Integer id) {
		caterepo.deleteById(id);
	}
	
	
}
