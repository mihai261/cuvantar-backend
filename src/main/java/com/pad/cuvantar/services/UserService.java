package com.pad.cuvantar.services;

import com.pad.cuvantar.exceptions.UserNotFoundException;
import com.pad.cuvantar.models.UserModel;
import com.pad.cuvantar.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    UserRepository userRepository;

    public UserModel getByUsername(String username) throws UserNotFoundException {
        List<UserModel> result = userRepository.findByUsername(username);
        if(result.size() == 0) throw new UserNotFoundException(String.format("User %s does not exist", username));
        return result.get(0);
    }

    public UserModel saveUser(UserModel u){
        return userRepository.save(u);
    }
}
