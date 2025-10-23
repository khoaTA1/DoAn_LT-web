package PKG.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.Stove;

@Repository
public interface StoveRepo extends JpaRepository<Stove, Long>{

}
