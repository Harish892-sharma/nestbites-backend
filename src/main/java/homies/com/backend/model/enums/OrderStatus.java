package homies.com.backend.model.enums;

public enum OrderStatus {

    PENDING,

    ACCEPTED,

    PREPARING,

    // Self-delivering chef is on the way
    OUT_FOR_DELIVERY,

    // Pickup-only chef: food is ready, waiting for the customer to collect
    READY_FOR_PICKUP,

    DELIVERED,

    PICKED_UP,

    CANCELLED
}