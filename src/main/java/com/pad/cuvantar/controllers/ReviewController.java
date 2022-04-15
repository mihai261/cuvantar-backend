package com.pad.cuvantar.controllers;

import com.pad.cuvantar.models.ReviewModel;
import com.pad.cuvantar.services.AuthService;
import com.pad.cuvantar.services.ReviewService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Resource
    AuthService authService;

    @Resource
    ReviewService reviewService;

    // doesn't work yet, java timezone different from sql timezone :(
    @GetMapping("/find")
    public List<ReviewModel> getReviewsForUser(@RequestParam String username) {

        if (!authService.checkAuthSessionExists(username)) throw new RuntimeException();

        return reviewService.getReviewsForUser(username);
    }

    @PatchMapping("/modify")
    public void updateReview(@RequestParam String username, @RequestParam int cardId,
                                    @RequestParam Optional<Boolean> passed) {

        if (!authService.checkAuthSessionExists(username)) throw new RuntimeException();

        if (passed.isPresent()) {
            reviewService.updateReviewForUser(username, cardId, passed.get());
        } else {
            reviewService.createReviewForUser(username, cardId);
        }
    }
}
