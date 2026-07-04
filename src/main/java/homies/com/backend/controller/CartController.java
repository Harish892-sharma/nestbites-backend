package homies.com.backend.controller;

import homies.com.backend.model.Cart;
import homies.com.backend.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart addToCart(@RequestBody Cart cart) {
        return cartService.addToCart(cart);
    }

    @GetMapping("/{userId}")
    public List<Cart> getCart(@PathVariable String userId) {
        return cartService.getCartByUser(userId);
    }

    @PutMapping("/{cartId}")
    public Cart updateQuantity(@PathVariable String cartId,
                               @RequestParam int quantity) {
        return cartService.updateCart(cartId, quantity);
    }

    @DeleteMapping("/{cartId}")
    public void removeItem(@PathVariable String cartId) {
        cartService.removeCartItem(cartId);
    }

    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }
}