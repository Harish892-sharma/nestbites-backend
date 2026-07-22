package homies.com.backend.security;

import homies.com.backend.exception.UnauthorizedException;
import homies.com.backend.model.Chef;
import homies.com.backend.model.User;
import homies.com.backend.repository.ChefRepository;
import homies.com.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Central place to know "who is actually calling this API right now".
 *
 * Never trust a userId / chefId sent in the request body or URL on its
 * own — always cross-check it against the identity attached to the JWT
 * via this class. This is what stops one customer from reading another
 * customer's cart/orders/address just by changing an id in the URL.
 */
@Component
public class CurrentUserUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChefRepository chefRepository;

    /** The logged-in user, resolved from the JWT-authenticated email. */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new UnauthorizedException("You must be logged in to do this.");
        }

        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Account not found for this session."));
    }

    public String getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(getCurrentUser().getRole());
    }

    /** Throws unless the caller IS the given user, or is an admin. */
    public void requireSelfOrAdmin(String userId) {
        User me = getCurrentUser();

        if ("ADMIN".equalsIgnoreCase(me.getRole())) return;

        if (!me.getId().equals(userId)) {
            throw new UnauthorizedException("You can't access another user's data.");
        }
    }

    /** The Chef profile id (not User id) owned by the logged-in chef. */
    public String getCurrentChefId() {
        return getCurrentChef().getId();
    }

    /** The full Chef profile document owned by the logged-in user. */
    public Chef getCurrentChef() {
        User me = getCurrentUser();

        return chefRepository.findByUserId(me.getId())
                .orElseThrow(() -> new UnauthorizedException("No chef profile linked to this account."));
    }

    /** Throws unless the caller owns the given Chef profile, or is an admin. */
    public void requireChefOwnerOrAdmin(String chefId) {
        User me = getCurrentUser();

        if ("ADMIN".equalsIgnoreCase(me.getRole())) return;

        if (!"CHEF".equalsIgnoreCase(me.getRole())) {
            throw new UnauthorizedException("Only the owning chef can do this.");
        }

        Chef chef = chefRepository.findByUserId(me.getId())
                .orElseThrow(() -> new UnauthorizedException("No chef profile linked to this account."));

        if (!chef.getId().equals(chefId)) {
            throw new UnauthorizedException("You can't access another chef's data.");
        }
    }
}
