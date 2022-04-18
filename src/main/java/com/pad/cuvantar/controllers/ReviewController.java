package com.pad.cuvantar.controllers;

import com.pad.cuvantar.exceptions.InvalidTokenException;
import com.pad.cuvantar.exceptions.ReviewAlreadyExistsException;
import com.pad.cuvantar.exceptions.ReviewNotFoundException;
import com.pad.cuvantar.exceptions.UserNotFoundException;
import com.pad.cuvantar.models.ReviewModel;
import com.pad.cuvantar.services.AuthService;
import com.pad.cuvantar.services.ReviewService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/find")
    public List<ReviewModel> getReviewsForUser(@RequestParam String username, @RequestHeader("custom-token") String token) throws UserNotFoundException, InvalidTokenException {

        if (!authService.checkAuthToken(username, token)) throw new InvalidTokenException("Provided token is invalid");

        return reviewService.getReviewsForUser(username);
    }

    @PatchMapping("/modify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReview(@RequestParam String username,
                             @RequestParam int cardId,
                             @RequestParam Optional<Boolean> passed,
                             @RequestHeader("custom-token") String token) throws UserNotFoundException, InvalidTokenException, ReviewNotFoundException, ReviewAlreadyExistsException {

        if (!authService.checkAuthToken(username, token)) throw new InvalidTokenException("Provided token is invalid");

        if (passed.isPresent()) {
            reviewService.updateReviewForUser(username, cardId, passed.get());
        } else {
            reviewService.createReviewForUser(username, cardId);
        }
    }
}