package com.pad.cuvantar.controllers;

import com.pad.cuvantar.exceptions.FlashcardAlreadyExistsException;
import com.pad.cuvantar.exceptions.FlashcardNotFoundException;
import com.pad.cuvantar.models.FlashcardModel;
import com.pad.cuvantar.services.FlashcardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "cuvantar-api")
@CrossOrigin(origins = "*")
public class FlashcardController {
    @Resource
    FlashcardService flashcardService;

    @Operation(summary = "Get a flashcard by the specified ID")
    @GetMapping("/cards/{id}")
    public FlashcardModel getCardById(@PathVariable int id) throws FlashcardNotFoundException {
        return flashcardService.getCardById(id);
    }

    @Operation(summary = "Get a list of all the existing flashcards")
    @GetMapping("/cards")
    public List<FlashcardModel> getAllCards()
    {
        return flashcardService.getAll();
    }

    @Operation(summary = "Add a new flashcard")
    @PostMapping("/cards")
    public FlashcardModel addCard(@RequestBody FlashcardModel flashcard) throws FlashcardAlreadyExistsException {
        if(flashcardService.checkCardExists(flashcard)) throw new FlashcardAlreadyExistsException(String.format("Flashcard with front %s already exists", flashcard.getFront()));

        return flashcardService.addFlashcard(flashcard);
    }

    @Operation(summary = "Delete a flashcard")
    @DeleteMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFlashcard(@PathVariable int id) throws FlashcardNotFoundException {
        flashcardService.deleteFlashcard(id);
    }
}
