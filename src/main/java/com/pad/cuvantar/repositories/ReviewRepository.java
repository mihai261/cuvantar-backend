package com.pad.cuvantar.repositories;

import com.pad.cuvantar.models.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Integer> {
}
