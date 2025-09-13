package khoa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import khoa.entity.User;

public interface UserService {

	void deleteById(Integer id);

	long count();

	<S extends User> boolean exists(Example<S> example);

	boolean existsById(Integer id);

	Optional<User> findById(Integer id);

	List<User> findAll();

	Page<User> findAll(Pageable pageable);

	<S extends User> S save(S entity);

	List<User> findByUserNameContaining(String nuame);

}
