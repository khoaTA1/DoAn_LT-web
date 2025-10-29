package PKG.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKG.entity.Cart;
import PKG.entity.Item;
import PKG.entity.User;
import PKG.repository.CartRepo;
import PKG.repository.item.ItemRepo;
import PKG.service.CartService;
import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Override
    public List<Cart> getCartByUser(User user) {
        return cartRepo.findByUser(user);
    }

    @Override
    public void addToCart(User user, Long itemId, int quantity) {
        Item item = itemRepo.findById(itemId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));
        Cart cart = cartRepo.findByUser(user).stream()
                .filter(c -> c.getItem().getId().equals(itemId))
                .findFirst()
                .orElse(new Cart(null, user, item, 0, item.getDiscount()));

        cart.setQuantity(cart.getQuantity() + quantity);
        cartRepo.save(cart);
    }

    @Transactional
    @Override
    public void removeFromCart(User user, Long itemId) {
        cartRepo.deleteByUserAndItemId(user, itemId);
    }

    @Override
    public void clearCart(User user) {
        List<Cart> carts = cartRepo.findByUser(user);
        cartRepo.deleteAll(carts);
    }

    @Override
    public double getTotalPrice(User user) {
        return getCartByUser(user).stream()
                .mapToDouble(Cart::getTotalPrice)
                .sum();
    }
}
