package homies.com.backend.controller;

import homies.com.backend.dto.review.CreateReviewRequest;
import homies.com.backend.dto.review.ReviewResponse;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @RequestBody CreateReviewRequest request) {

        // Reviews are always posted as whoever is logged in — you can't
        // write a review pretending to be another customer.
        request.setUserId(currentUserUtil.getCurrentUserId());
        return ResponseEntity.ok(reviewService.addReview(request));
    }

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<ReviewResponse>> getChefReviews(
            @PathVariable String chefId) {

        return ResponseEntity.ok(reviewService.getChefReviews(chefId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getUserReviews(
            @PathVariable String userId) {

        currentUserUtil.requireSelfOrAdmin(userId);
        return ResponseEntity.ok(reviewService.getUserReviews(userId));
    }
}
