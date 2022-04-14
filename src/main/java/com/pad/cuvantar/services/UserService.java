package com.pad.cuvantar.services;

import com.pad.cuvantar.models.UserModel;
import com.pad.cuvantar.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    UserRepository userRepository;

    // TODO: make specific exceptions and handlers for the API operations
    public UserModel getByUsername(String username) throws RuntimeException {
        List<UserModel> result = userRepository.findByUsername(username);
        if(result.size() == 0) throw new RuntimeException();
        return result.get(0);
    }

    public UserModel saveUser(UserModel u){
        return userRepository.save(u);
    }
}
