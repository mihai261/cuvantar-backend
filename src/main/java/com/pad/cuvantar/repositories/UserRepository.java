package com.pad.cuvantar.repositories;

import com.pad.cuvantar.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    List<UserModel> findByUsername(String username);
    List<UserModel> findByEmail(String email);
}
