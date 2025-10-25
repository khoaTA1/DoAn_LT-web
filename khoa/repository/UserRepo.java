package PKG.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>  {
	List<User> findByUserNameContaining(String uname);
	Optional<User> findByUserName(String uname);
	boolean existsByUserName(String uname);
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
}
