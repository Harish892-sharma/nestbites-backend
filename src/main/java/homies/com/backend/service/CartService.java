package homies.com.backend.service;

import homies.com.backend.model.Cart;
import java.util.List;

public interface CartService {

    Cart addToCart(Cart cart);

    List<Cart> getCartByUser(String userId);

    Cart updateCart(String cartId, int quantity);

    void removeCartItem(String cartId);

    void clearCart(String userId);
}