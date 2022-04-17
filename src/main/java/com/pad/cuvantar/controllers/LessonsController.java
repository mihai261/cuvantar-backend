package com.pad.cuvantar.controllers;

import com.pad.cuvantar.models.FlashcardModel;
import com.pad.cuvantar.services.AuthService;
import  com.pad.cuvantar.services.ReviewService;
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
    public  List<FlashcardModel> getNewLesson(@RequestParam String username)
    {
        if(!authService.checkAuthSessionExists(username)) throw new RuntimeException();

        return lessonService.getNewLesson(username);
    }

}
