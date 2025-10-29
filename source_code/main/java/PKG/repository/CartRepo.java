package PKG.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.Cart;
import PKG.entity.User;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    void deleteByUserAndItemId(User user, Long itemId);
}
