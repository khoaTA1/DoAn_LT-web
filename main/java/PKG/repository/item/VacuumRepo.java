package PKG.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.Vacuum;

@Repository
public interface VacuumRepo extends JpaRepository<Vacuum, Long>{

}
