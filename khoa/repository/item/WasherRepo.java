package PKG.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.Washer;

@Repository
public interface WasherRepo extends JpaRepository<Washer, Long> {

}
