package PKG.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PKG.entity.Password;

public interface PassRepo extends JpaRepository<Password, Long>{
	Optional<Password> findByUserId(long userid);
}
