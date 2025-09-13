package khoa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import khoa.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	List<User> findByUserNameContaining(String nuame);
}
