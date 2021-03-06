package com.pad.cuvantar.controllers;

import com.pad.cuvantar.exceptions.InvalidTokenException;
import com.pad.cuvantar.exceptions.ReviewAlreadyExistsException;
import com.pad.cuvantar.exceptions.ReviewNotFoundException;
import com.pad.cuvantar.exceptions.UserNotFoundException;
import com.pad.cuvantar.models.ReviewModel;
import com.pad.cuvantar.services.AuthService;
import com.pad.cuvantar.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "cuvantar-api")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Resource
    AuthService authService;

    @Resource
    ReviewService reviewService;

    @Operation(summary = "Get all available review items for a user")
    @GetMapping(value="/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReviewModel> getReviewsForUser(@RequestParam String username, @RequestHeader("custom-token") String token, @RequestParam  Optional<Boolean> recent) throws UserNotFoundException, InvalidTokenException {

        if (!authService.checkAuthToken(username, token)) throw new InvalidTokenException("Provided token is invalid");

        if(recent.isPresent()){
            return reviewService.getReviewsForUser(username, recent.get());
        }
        return reviewService.getReviewsForUser(username);
    }

    @Operation(summary = "Create or update a review item")
    @PostMapping("/reviews")
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
