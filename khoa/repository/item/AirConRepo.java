package PKG.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.airCon;

@Repository
public interface AirConRepo extends JpaRepository<airCon, Long> {

}
