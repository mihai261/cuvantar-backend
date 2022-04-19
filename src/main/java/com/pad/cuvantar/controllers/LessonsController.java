package com.pad.cuvantar.controllers;

import com.pad.cuvantar.exceptions.InvalidTokenException;
import com.pad.cuvantar.exceptions.UserNotFoundException;
import com.pad.cuvantar.exceptions.NoNewFlashCardsExceptions;
import com.pad.cuvantar.models.FlashcardModel;
import com.pad.cuvantar.services.AuthService;
import com.pad.cuvantar.services.LessonService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonsController {

    @Resource
    AuthService authService;

    @Resource
    LessonService lessonService;

    @GetMapping("/lessons")
    public  List<FlashcardModel> getNewLesson(@RequestParam String username, @RequestHeader("custom-token") String token) throws UserNotFoundException, InvalidTokenException, NoNewFlashCardsExceptions
    {
        if(!authService.checkAuthToken(username, token)) throw new InvalidTokenException("Provided token is invalid");

        if(lessonService.getNewLesson(username)==null)
        {
            throw new NoNewFlashCardsExceptions("There are no flashcards left for you. Sorry");
        }
        return lessonService.getNewLesson(username);
    }

}
