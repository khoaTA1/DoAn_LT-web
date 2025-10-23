package PKG.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.Fan;

@Repository
public interface FanRepo extends JpaRepository<Fan, Long> {
	
}
