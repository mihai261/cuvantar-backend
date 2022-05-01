package com.pad.cuvantar.services;

import com.pad.cuvantar.exceptions.FlashcardNotFoundException;
import com.pad.cuvantar.models.FlashcardModel;
import com.pad.cuvantar.models.ReviewModel;
import com.pad.cuvantar.repositories.FlashcardRepository;
import com.pad.cuvantar.repositories.ReviewRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {
    @Resource
    FlashcardRepository flashcardRepository;

    @Resource
    ReviewRepository reviewRepository;

    public FlashcardModel getCardById(int id) throws FlashcardNotFoundException {
        Optional<FlashcardModel> o_flashcard;
        o_flashcard = flashcardRepository.findById(id);
        if(o_flashcard.isEmpty()){
            throw new FlashcardNotFoundException(String.format("Flashcard with %s id not found", id));
        }
        return o_flashcard.get();
    }

    public List<FlashcardModel> getAll(){
        return flashcardRepository.findAll();
    }

    public FlashcardModel addFlashcard(FlashcardModel flashcardModel){
        return flashcardRepository.save(flashcardModel);
    }

    public boolean checkCardExists(FlashcardModel flashcard){
        List<FlashcardModel> res = flashcardRepository.findCardByFront(flashcard.getFront());

        return res.size() != 0;
    }

    public void deleteFlashcard(int id) throws FlashcardNotFoundException {
        try {
            List<ReviewModel> reviews = reviewRepository.findAll();
            for(ReviewModel r : reviews){
                if(r.getCard_id() == id) reviewRepository.deleteById(r.getId());
            }
            flashcardRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new FlashcardNotFoundException(String.format("Flash card with id %s does not exist", id), e);
        }
    }
}
