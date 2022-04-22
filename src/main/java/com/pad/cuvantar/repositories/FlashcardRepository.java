package com.pad.cuvantar.repositories;

import com.pad.cuvantar.models.FlashcardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<FlashcardModel, Integer> {

    @Query(value = "SELECT * FROM flashcard LEFT JOIN review ON review.card_id = flashcard.id AND review.user_id = ?1 WHERE review.card_id IS NULL LIMIT 5", nativeQuery = true)
    List<FlashcardModel> findCardForUser(Integer userId);
    @Query(value = "SELECT * FROM flashcard WHERE flashcard.front = ?1", nativeQuery = true)
    List<FlashcardModel> findCardByFront(String front);
    @Query(value = "SELECT * FROM flashcard WHERE flashcard.back = ?1", nativeQuery = true)
    List<FlashcardModel> findCardByBack(String back);
}