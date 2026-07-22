package homies.com.backend.controller;

import homies.com.backend.model.Cart;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart addToCart(@RequestBody Cart cart) {
        // Ignore whatever userId the client sent — always attach the
        // cart item to whoever is actually logged in.
        cart.setUserId(currentUserUtil.getCurrentUserId());
        return cartService.addToCart(cart);
    }

    @GetMapping("/{userId}")
    public List<Cart> getCart(@PathVariable String userId) {
        currentUserUtil.requireSelfOrAdmin(userId);
        return cartService.getCartByUser(userId);
    }

    @PutMapping("/{cartId}")
    public Cart updateQuantity(
            @PathVariable String cartId,
            @RequestParam int quantity
    ) {
        currentUserUtil.requireSelfOrAdmin(cartService.getCartItem(cartId).getUserId());
        return cartService.updateCart(cartId, quantity);
    }

    @DeleteMapping("/{cartId}")
    public void removeItem(@PathVariable String cartId) {
        currentUserUtil.requireSelfOrAdmin(cartService.getCartItem(cartId).getUserId());
        cartService.removeCartItem(cartId);
    }

    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable String userId) {
        currentUserUtil.requireSelfOrAdmin(userId);
        cartService.clearCart(userId);
    }
}
