package com.pad.cuvantar.services;

import com.pad.cuvantar.exceptions.UserNotFoundException;
import com.pad.cuvantar.models.FlashcardModel;
import com.pad.cuvantar.repositories.FlashcardRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LessonService {

    @Resource
    FlashcardRepository flashcardRepository;

    @Resource
    UserService userService;

    public List<FlashcardModel> getNewLesson(String username) throws UserNotFoundException{

        int userId = userService.getByUsername(username).getId();

        return flashcardRepository.findCardForUser(userId);
    }


}
