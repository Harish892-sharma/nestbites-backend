package homies.com.backend.service.impl;

import homies.com.backend.dto.review.CreateReviewRequest;
import homies.com.backend.dto.review.ReviewResponse;
import homies.com.backend.model.review.Review;
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

    @Override
    public ReviewResponse addReview(CreateReviewRequest request) {

        if (reviewRepository.existsByOrderId(request.getOrderId())) {
            throw new RuntimeException("Review already submitted for this order.");
        }

        Review review = new Review();

        review.setOrderId(request.getOrderId());

        review.setUserId(request.getUserId());
        review.setUserName(request.getUserName());

        review.setChefId(request.getChefId());
        review.setChefName(request.getChefName());

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review saved = reviewRepository.save(review);

        return convert(saved);
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