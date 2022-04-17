package com.pad.cuvantar.services;

import  com.pad.cuvantar.models.FlashcardModel;
import com.pad.cuvantar.repositories.AuthSessionRepository;
import com.pad.cuvantar.repositories.FlashcardRepository;
import com.pad.cuvantar.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LessonService {

    @Resource
    FlashcardRepository flashcardRepository;

    @Resource
    UserService userService;

    public List<FlashcardModel> getNewLesson(String username) {

        int userId = userService.getByUsername(username).getId();

        return flashcardRepository.findUnusedFlashCardForUser(userId);
    }


}
