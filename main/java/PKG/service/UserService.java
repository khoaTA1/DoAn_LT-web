package PKG.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import PKG.entity.User;

public interface UserService {

	void deleteById(Long id);

	long count();

	<S extends User> boolean exists(Example<S> example);

	boolean existsById(Long id);

	Optional<User> findById(Long id);

	List<User> findAll();

	Page<User> findAll(Pageable pageable);

	<S extends User> S save(S entity);

	List<User> findByUserNameContaining(String nuame);

	boolean existsByUserName(String uname);

	boolean existsByEmail(String email);

	Optional<User> findByUserName(String uname);

}
