package PKG.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.Tivi;

@Repository
public interface TiviRepo extends JpaRepository<Tivi, Long> {

}
