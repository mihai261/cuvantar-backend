package com.pad.cuvantar.services;

import com.pad.cuvantar.exceptions.ReviewAlreadyExistsException;
import com.pad.cuvantar.exceptions.ReviewNotFoundException;
import com.pad.cuvantar.exceptions.UserNotFoundException;
import com.pad.cuvantar.models.ReviewModel;
import com.pad.cuvantar.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ReviewService {

    private static final int MAX_LEVEL = 5;

    @Value("${cuvantar.intervals}")
    private List<Long> intervals;

    @Resource
    ReviewRepository reviewRepository;

    @Resource
    UserService userService;

    public List<ReviewModel> getReviewsForUser(String username) throws UserNotFoundException {
        int userId = userService.getByUsername(username).getId();

        return reviewRepository.findDueReviewsForUser(userId);
    }

    public List<ReviewModel> getReviewsForUser(String username, Boolean recent) throws UserNotFoundException {
        int userId = userService.getByUsername(username).getId();

        if(!recent) return reviewRepository.findDueReviewsForUser(userId);
        else return reviewRepository.findNewestReviewsForUser(userId);
    }

    public void createReviewForUser(String username, int cardId) throws UserNotFoundException, ReviewAlreadyExistsException {
        int userId = userService.getByUsername(username).getId();

        if (reviewRepository.findReviewByUserAndCard(userId, cardId) != null) {
            throw new ReviewAlreadyExistsException("Review already exists for user " + username + " and card " + cardId);
        }

        ReviewModel review = new ReviewModel();
        review.setUser_id(userId);
        review.setCard_id(cardId);
        review.setLevel(0);
        review.setDue_date(calculateDueDate(0));

        reviewRepository.save(review);
    }

    public void updateReviewForUser(String username, int cardId, Boolean passed) throws UserNotFoundException, ReviewNotFoundException {
        int userId = userService.getByUsername(username).getId();

        ReviewModel review = reviewRepository.findReviewByUserAndCard(userId, cardId);

        if (review == null) throw new ReviewNotFoundException("Review not found for user " + username + " and card " + cardId);

        int level = review.getLevel();
        if (passed) {
            level += (level == MAX_LEVEL ? 0 : 1);
        } else {
            level -= (level == 0 ? 0 : 1);
        }

        reviewRepository.updateReview(review.getId(), level, calculateDueDate(level));
    }

    private Timestamp calculateDueDate(int level) {
        return new Timestamp(System.currentTimeMillis() + intervals.get(level));
    }
}
