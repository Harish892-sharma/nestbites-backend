package homies.com.backend.controller;

import homies.com.backend.dto.review.CreateReviewRequest;
import homies.com.backend.dto.review.ReviewResponse;
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

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @RequestBody CreateReviewRequest request) {

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

        return ResponseEntity.ok(reviewService.getUserReviews(userId));
    }
}