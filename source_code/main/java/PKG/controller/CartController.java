package PKG.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import PKG.entity.Cart;
import PKG.entity.User;
import PKG.model.inputCard;
import PKG.service.CartService;
import PKG.service.UserService;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        if (principal == null) return "redirect:/user/login";
        User user = userService.findByUserName(principal.getName()).orElse(null);

        List<Cart> carts = cartService.getCartByUser(user);
        double total = cartService.getTotalPrice(user);

        model.addAttribute("cartList", carts);
        model.addAttribute("totalPrice", total);
        return "user/cart";
    }

    @ResponseBody
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody inputCard cart,
                            Principal principal) {
        User user = userService.findByUserName(principal.getName()).orElseThrow();
        cartService.addToCart(user, cart.getItemId(), cart.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/remove")
    public String removeFromCart(@RequestParam("itemId") Long itemId, Principal principal) {
        User user = userService.findByUserName(principal.getName()).orElseThrow();
        cartService.removeFromCart(user, itemId);
        return "redirect:/user/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName()).orElseThrow();
        double total = cartService.getTotalPrice(user);
        model.addAttribute("tongTien", total);
        return "redirect:/redirect/payment";
    }
}
