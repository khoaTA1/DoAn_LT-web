package PKG.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.Cooker;

@Repository
public interface CookerRepo extends JpaRepository<Cooker, Long> {

}