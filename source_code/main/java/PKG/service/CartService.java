package PKG.service;

import java.util.List;

import PKG.entity.Cart;
import PKG.entity.User;

public interface CartService {
    List<Cart> getCartByUser(User user);
    void addToCart(User user, Long itemId, int quantity);
    void removeFromCart(User user, Long itemId);
    void clearCart(User user);
    double getTotalPrice(User user);
}
