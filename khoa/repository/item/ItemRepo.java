package PKG.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;

import PKG.entity.Item;

public interface ItemRepo extends JpaRepository<Item, Long> {

}
