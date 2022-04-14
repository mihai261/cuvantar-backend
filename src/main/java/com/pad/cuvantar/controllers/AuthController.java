package com.pad.cuvantar.controllers;

import com.pad.cuvantar.models.AuthSessionModel;
import com.pad.cuvantar.models.UserModel;
import com.pad.cuvantar.repositories.UserRepository;
import com.pad.cuvantar.services.AuthService;
import com.pad.cuvantar.services.UserService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    AuthService authService;

    @Resource
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) throws RuntimeException {
        if(authService.checkAuthSessionExists(username)) throw new RuntimeException();

        UserModel dbuser = userService.getByUsername(username);
        if(authService.encodePassword(password, username).equals(dbuser.getPassword())){
            AuthSessionModel authSession = new AuthSessionModel();
            String token = UUID.randomUUID().toString();
            authSession.setToken(token);
            authSession.setUser_id(dbuser.getId());
            authSession.setUsername(dbuser.getUsername());

            authService.saveAuthSession(authSession);

            return token;
        }

        throw new RuntimeException();
    }

    @PostMapping("/logout")
    public void logout(@RequestParam String username, @RequestParam String token) throws RuntimeException {
        if(!authService.checkAuthSessionExists(username)) throw new RuntimeException();

        UserModel dbuser = userService.getByUsername(username);
        if(authService.checkAuthToken(username, token)){
            authService.deleteSession(username);
            return;
        }

        throw new RuntimeException();
    }

    @PostMapping("/register")
    public UserModel addUser(@RequestBody UserModel user) throws RuntimeException {
        if(authService.checkUsernameAndEmailExist(user.getUsername(), user.getEmail())) throw new RuntimeException();

        user.setPassword(authService.encodePassword(user.getPassword(), user.getUsername()));

        return userService.saveUser(user);
    }

}
