package com.pad.cuvantar.controllers;

import com.pad.cuvantar.exceptions.InvalidTokenException;
import com.pad.cuvantar.exceptions.UserNotFoundException;
import com.pad.cuvantar.models.FlashcardModel;
import com.pad.cuvantar.services.AuthService;
import com.pad.cuvantar.services.LessonService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@SecurityRequirement(name = "cuvantar-api")
public class LessonsController {

    @Resource
    AuthService authService;

    @Resource
    LessonService lessonService;

    @GetMapping("/find")
    public  List<FlashcardModel> getNewLesson(@RequestParam String username, @RequestHeader("custom-token") String token) throws UserNotFoundException, InvalidTokenException
    {
        if(!authService.checkAuthToken(username, token)) throw new InvalidTokenException("Provided token is invalid");

        return lessonService.getNewLesson(username);
    }

}
