package homies.com.backend.service.impl;

import homies.com.backend.exception.ResourceNotFoundException;
import homies.com.backend.model.Cart;
import homies.com.backend.repository.CartRepository;
import homies.com.backend.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addToCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCartByUser(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart getCartItem(String cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
    }

    @Override
    public Cart updateCart(String cartId, int quantity) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    @Override
    public void removeCartItem(String cartId) {

        if (!cartRepository.existsById(cartId)) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        cartRepository.deleteById(cartId);
    }

    @Override
    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}