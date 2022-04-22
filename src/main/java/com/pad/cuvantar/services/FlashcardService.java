package com.pad.cuvantar.services;

import com.pad.cuvantar.exceptions.FlashcardNotFoundException;
import com.pad.cuvantar.models.FlashcardModel;
import com.pad.cuvantar.repositories.FlashcardRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {
    @Resource
    FlashcardRepository flashcardRepository;

    public FlashcardModel getCardById(int id) throws FlashcardNotFoundException {
        Optional<FlashcardModel> o_flashcard;
        o_flashcard = flashcardRepository.findById(id);
        if(o_flashcard.isEmpty()){
            throw new FlashcardNotFoundException(String.format("Flashcard with %s id not found", id));
        }
        return o_flashcard.get();
    }

    public List<FlashcardModel> getCardByFront(String front){
        return flashcardRepository.findCardByFront(front);
    }

    public List<FlashcardModel> getCardByBack(String back){
        return flashcardRepository.findCardByBack(back);
    }

    public FlashcardModel addFlashcard(FlashcardModel flashcardModel){
        return flashcardRepository.save(flashcardModel);
    }

    public boolean checkCardExists(FlashcardModel flashcard){
        List<FlashcardModel> res = flashcardRepository.findCardByFront(flashcard.getFront());

        return res.size() != 0;
    }
}
