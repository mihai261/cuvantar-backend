package com.pad.cuvantar.repositories;

import com.pad.cuvantar.models.AuthSessionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthSessionRepository extends JpaRepository<AuthSessionModel, Integer> {
}
