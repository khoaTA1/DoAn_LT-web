package khoa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import khoa.entity.User;
import khoa.repo.UserRepo;
import khoa.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userrepo;

	@Override
	public List<User> findByUserNameContaining(String nuame) {
		return userrepo.findByUserNameContaining(nuame);
	}

	@Override
	public <S extends User> S save(S entity) {
		return userrepo.save(entity);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userrepo.findAll(pageable);
	}

	@Override
	public List<User> findAll() {
		return userrepo.findAll();
	}

	@Override
	public Optional<User> findById(Integer id) {
		return userrepo.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return userrepo.existsById(id);
	}

	@Override
	public <S extends User> boolean exists(Example<S> example) {
		return userrepo.exists(example);
	}

	@Override
	public long count() {
		return userrepo.count();
	}

	@Override
	public void deleteById(Integer id) {
		userrepo.deleteById(id);
	}
	
	
}
