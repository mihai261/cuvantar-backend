package com.pad.cuvantar.controllers;

import com.pad.cuvantar.exceptions.FlashcardAlreadyExistsException;
import com.pad.cuvantar.exceptions.FlashcardNotFoundException;
import com.pad.cuvantar.models.FlashcardModel;
import com.pad.cuvantar.services.FlashcardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "cuvantar-api")
public class FlashcardController {
    @Resource
    FlashcardService flashcardService;

    @Operation(summary = "Get a card by the specified ID")
    @GetMapping("/cards/{id}")
    public FlashcardModel getCardById(@PathVariable int id) throws FlashcardNotFoundException {
        return flashcardService.getCardById(id);
    }

    @Operation(summary = "Get a list of cards matching the specified keyword on front or back")
    @GetMapping("/cards/search")
    public List<FlashcardModel> searchForCards(@RequestParam String keyword, @RequestParam(defaultValue = "true", required = false) boolean searchByFront)
    {
        if(searchByFront) {
            return flashcardService.getCardByFront(keyword);
        }
        else{
            return flashcardService.getCardByBack(keyword);
        }
    }

    @Operation(summary = "Add a new flashcard")
    @PostMapping("/cards")
    public FlashcardModel addCard(@RequestBody FlashcardModel flashcard) throws FlashcardAlreadyExistsException {
        if(flashcardService.checkCardExists(flashcard)) throw new FlashcardAlreadyExistsException(String.format("Flashcard with front %s already exists", flashcard.getFront()));

        return flashcardService.addFlashcard(flashcard);
    }
}
