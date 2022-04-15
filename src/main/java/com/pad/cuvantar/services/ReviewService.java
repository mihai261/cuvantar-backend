package com.pad.cuvantar.services;

import com.pad.cuvantar.models.ReviewModel;
import com.pad.cuvantar.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private static final int MAX_LEVEL = 5;
    // 6 hours -> 1 day -> 2 days -> 1 week -> 2 weeks -> 1 month(ish)
    private static final long[] INTERVAL = {
            6L,
            24L,
            48L,
            168L,
            336L,
            730L,
    };

    @Resource
    ReviewRepository reviewRepository;

    @Resource
    UserService userService;

    public List<ReviewModel> getReviewsForUser(String username) {
        int userId = userService.getByUsername(username).getId();

        return reviewRepository.findReviewsForUser(userId);
    }

    public void createReviewForUser(String username, int cardId) {
        int userId = userService.getByUsername(username).getId();

        // review already exists
        if (reviewRepository.findReviewByUserAndCard(userId, cardId) != null) {
            throw new RuntimeException();
        }

        ReviewModel review = new ReviewModel();
        review.setUser_id(userId);
        review.setCard_id(cardId);
        review.setLevel(0);
        review.setDue_date(calculateDueDate(0));

        reviewRepository.save(review);
    }

    public void updateReviewForUser(String username, int cardId, Boolean passed) {
        int userId = userService.getByUsername(username).getId();

        ReviewModel review = reviewRepository.findReviewByUserAndCard(userId, cardId);
        // review doesn't exist
        if (review == null) throw new RuntimeException();

        int level = review.getLevel();
        if (passed) {
            level += (level == MAX_LEVEL ? 0 : 1);
        } else {
            level -= (level == 0 ? 0 : 1);
        }

        reviewRepository.updateReview(review.getId(), level, calculateDueDate(level));
    }

    private LocalDateTime calculateDueDate(int level) {
        LocalDateTime crtTime = LocalDateTime.now();
        return crtTime.plusHours(INTERVAL[level]);
    }
}
