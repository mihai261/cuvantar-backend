package com.pad.cuvantar.controllers;

import com.pad.cuvantar.exceptions.*;
import com.pad.cuvantar.models.AuthSessionModel;
import com.pad.cuvantar.models.UserModel;
import com.pad.cuvantar.services.AuthService;
import com.pad.cuvantar.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    AuthService authService;

    @Resource
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) throws SessionException, InvalidCredentialsException, UserNotFoundException {
        if(authService.checkAuthSessionExists(username)) throw new SessionException("Session already exists");

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

        throw new InvalidCredentialsException("Specified credentials are invalid");
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestParam String username, @RequestParam String token) throws UserNotFoundException, InvalidTokenException, SessionException {
        if(!authService.checkAuthSessionExists(username)) throw new SessionException("Session does not exists");

        if(authService.checkAuthToken(username, token)){
            authService.deleteSession(username);
            return;
        }

        throw new InvalidTokenException("Invalid token for this operation");
    }

    @PostMapping("/register")
    public UserModel addUser(@RequestBody UserModel user) throws UserAlreadyExistsException, EmailAlreadyExistsException {
        if(authService.checkUsernameExists(user.getUsername())) throw new UserAlreadyExistsException(String.format("An account with username %s already exists", user.getUsername()));

        if(authService.checkEmailExists(user.getEmail())) throw new EmailAlreadyExistsException(String.format("An account with email %s already exists", user.getEmail()));

        user.setPassword(authService.encodePassword(user.getPassword(), user.getUsername()));

        return userService.saveUser(user);
    }

}
