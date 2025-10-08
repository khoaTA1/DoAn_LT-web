package PKG.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import PKG.entity.User;
import PKG.repository.UserRepo;
import PKG.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userrepo;

	@Override
	public boolean existsByUserName(String uname) {
		return userrepo.existsByUserName(uname);
	}

	@Override
	public List<User> findByUserNameContaining(String uname) {
		return userrepo.findByUserNameContaining(uname);
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
	public Optional<User> findById(Long id) {
		return userrepo.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return userrepo.existsById(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userrepo.existsByEmail(email);
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
	public void deleteById(Long id) {
		userrepo.deleteById(id);
	}

	@Override
	public Optional<User> findByUserName(String uname) {
		return userrepo.findByUserName(uname);
	}
	
	
}
