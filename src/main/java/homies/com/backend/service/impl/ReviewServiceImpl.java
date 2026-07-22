package homies.com.backend.service.impl;

import homies.com.backend.dto.review.CreateReviewRequest;
import homies.com.backend.dto.review.ReviewResponse;
import homies.com.backend.exception.BadRequestException;
import homies.com.backend.model.Chef;
import homies.com.backend.model.Order;
import homies.com.backend.model.enums.OrderStatus;
import homies.com.backend.model.review.Review;
import homies.com.backend.repository.ChefRepository;
import homies.com.backend.repository.OrderRepository;
import homies.com.backend.repository.ReviewRepository;
import homies.com.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ChefRepository chefRepository;

    @Override
    public ReviewResponse addReview(CreateReviewRequest request) {

        if (reviewRepository.existsByOrderId(request.getOrderId())) {
            throw new BadRequestException("You've already reviewed this order.");
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new BadRequestException("Rating must be between 1 and 5.");
        }

        // A review must be tied to a real order that this exact customer
        // placed, and that order must actually have been delivered —
        // this is what keeps ratings genuine instead of anyone being
        // able to post reviews for kitchens they never ordered from.
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new BadRequestException("Order not found."));

        if (!order.getUserId().equals(request.getUserId())) {
            throw new BadRequestException("This order doesn't belong to you.");
        }

        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new BadRequestException("You can only review orders that have been delivered.");
        }

        Chef chef = chefRepository.findById(order.getChefId())
                .orElseThrow(() -> new BadRequestException("Kitchen not found."));

        Review review = new Review();

        review.setOrderId(request.getOrderId());

        review.setUserId(request.getUserId());
        review.setUserName(request.getUserName());

        review.setChefId(chef.getId());
        review.setChefName(chef.getKitchenName());

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review saved = reviewRepository.save(review);

        recalculateChefRating(chef.getId());

        return convert(saved);
    }

    /**
     * Recomputes a chef's rating and review count from the real reviews
     * in the database — this is what makes the number shown on a chef's
     * card genuinely grow with real customer feedback instead of sitting
     * at whatever placeholder value it started at.
     */
    private void recalculateChefRating(String chefId) {

        List<Review> reviews = reviewRepository.findByChefId(chefId);

        double average = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        chefRepository.findById(chefId).ifPresent(chef -> {
            chef.setRating(Math.round(average * 10.0) / 10.0);
            chef.setTotalReviews(reviews.size());
            chefRepository.save(chef);
        });
    }

    @Override
    public List<ReviewResponse> getChefReviews(String chefId) {

        List<ReviewResponse> responses = new ArrayList<>();

        for (Review review : reviewRepository.findByChefId(chefId)) {
            responses.add(convert(review));
        }

        return responses;
    }

    @Override
    public List<ReviewResponse> getUserReviews(String userId) {

        List<ReviewResponse> responses = new ArrayList<>();

        for (Review review : reviewRepository.findByUserId(userId)) {
            responses.add(convert(review));
        }

        return responses;
    }

    private ReviewResponse convert(Review review) {

        ReviewResponse response = new ReviewResponse();

        response.setReviewId(review.getId());
        response.setUserName(review.getUserName());
        response.setChefName(review.getChefName());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setCreatedAt(review.getCreatedAt());

        return response;
    }
}