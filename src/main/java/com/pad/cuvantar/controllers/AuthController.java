package com.pad.cuvantar.controllers;

import com.pad.cuvantar.exceptions.*;
import com.pad.cuvantar.models.AuthSessionModel;
import com.pad.cuvantar.models.AuthTokenModel;
import com.pad.cuvantar.models.UserModel;
import com.pad.cuvantar.services.AuthService;
import com.pad.cuvantar.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "cuvantar-api")
@CrossOrigin(origins = "*")
public class AuthController {

    @Resource
    AuthService authService;

    @Resource
    UserService userService;

    @Operation(summary = "Start a new auth session")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthTokenModel login(@RequestParam String username, @RequestParam String password) throws InvalidCredentialsException, UserNotFoundException {
        UserModel dbuser = userService.getByUsername(username);
        if(authService.encodePassword(password, username).equals(dbuser.getPassword())){
            AuthSessionModel authSession = new AuthSessionModel();
            String token = UUID.randomUUID().toString();
            authSession.setToken(token);
            authSession.setUser_id(dbuser.getId());
            authSession.setUsername(dbuser.getUsername());
            authSession.setCreated_at(new Timestamp(System.currentTimeMillis()));

            authService.saveAuthSession(authSession);

            return new AuthTokenModel(token);
        }

        throw new InvalidCredentialsException("Specified credentials are invalid");
    }

    @Operation(summary = "Terminate an existing auth session")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestParam String username, @RequestHeader("custom-token") String token) throws UserNotFoundException, InvalidTokenException, SessionException {
        if(!authService.checkAuthSessionExists(username)) throw new SessionException("Session does not exists");

        if(authService.checkAuthToken(username, token)){
            authService.deleteSession(username, token);
            return;
        }

        throw new InvalidTokenException("Invalid token for this operation");
    }

    @Operation(summary = "Create a new user")
    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel addUser(@RequestBody UserModel user) throws UserAlreadyExistsException, EmailAlreadyExistsException {
        if(authService.checkUsernameExists(user.getUsername())) throw new UserAlreadyExistsException(String.format("An account with username %s already exists", user.getUsername()));

        if(authService.checkEmailExists(user.getEmail())) throw new EmailAlreadyExistsException(String.format("An account with email %s already exists", user.getEmail()));

        user.setPassword(authService.encodePassword(user.getPassword(), user.getUsername()));

        return userService.saveUser(user);
    }

}
