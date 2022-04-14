package com.pad.cuvantar.repositories;

import com.pad.cuvantar.models.FlashcardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardRepository extends JpaRepository<FlashcardModel, Integer> {
}