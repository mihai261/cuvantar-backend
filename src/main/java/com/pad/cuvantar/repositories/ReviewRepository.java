package com.pad.cuvantar.repositories;

import com.pad.cuvantar.models.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Integer> {

    @Query(value = "SELECT * FROM review WHERE user_id = ?1 AND due_date < now()",
            nativeQuery = true)
    List<ReviewModel> findDueReviewsForUser(Integer userId);

    @Query(value = "SELECT * FROM review WHERE user_id = ?1 ORDER BY id DESC",
            nativeQuery = true)
    List<ReviewModel> findNewestReviewsForUser(Integer userId);

    @Query(value = "SELECT * FROM review WHERE user_id = ?1 AND card_id = ?2",
            nativeQuery = true)
    ReviewModel findReviewByUserAndCard(Integer userId, Integer cardId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE review SET level = ?2, due_date = ?3 WHERE id = ?1",
            nativeQuery = true)
    void updateReview(Integer reviewId, Integer level, Timestamp dueDate);
}
