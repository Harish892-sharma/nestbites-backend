package homies.com.backend.service;

import homies.com.backend.dto.review.CreateReviewRequest;
import homies.com.backend.dto.review.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse addReview(CreateReviewRequest request);

    List<ReviewResponse> getChefReviews(String chefId);

    List<ReviewResponse> getUserReviews(String userId);
}