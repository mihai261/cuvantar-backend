package com.pad.cuvantar.services;

import com.pad.cuvantar.exceptions.UserNotFoundException;
import com.pad.cuvantar.models.AuthSessionModel;
import com.pad.cuvantar.models.UserModel;
import com.pad.cuvantar.repositories.AuthSessionRepository;
import com.pad.cuvantar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

// this means it can be used as a @Component and thus can be autowired
@Service
public class AuthService {
    @Resource
    UserRepository userRepository;

    @Resource
    AuthSessionRepository authSessionRepository;

    @Value("${cuvantar.auth.expiration}")
    String expirationDeltaString;

    public String encodePassword(String password, String saltString){
        byte[] hash = new byte[0];
        try{
            byte[] salt = saltString.getBytes(StandardCharsets.UTF_8);

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(hash);
    }

    public boolean checkAuthToken(String username, String token){
        List<AuthSessionModel> activeSessions = authSessionRepository.findAll().stream()
                .filter(x -> (x.getUsername().equals(username) && x.getToken().equals(token)))
                .collect(Collectors.toList());

        return activeSessions.size() != 0;
    }

    public boolean checkAuthSessionExists(String username){
        List<AuthSessionModel> activeSessions = authSessionRepository.findAll().stream()
                .filter(x -> (x.getUsername().equals(username)))
                .collect(Collectors.toList());

        return activeSessions.size() != 0;
    }

    public void saveAuthSession(AuthSessionModel au){
        authSessionRepository.save(au);
    }

    public void deleteSession(String username, String token) throws UserNotFoundException {
        AuthSessionModel session = getByUsernameAndToken(username, token);
        authSessionRepository.deleteById(session.getId());
    }

    @Scheduled(fixedRateString="${cuvantar.auth.refresh.rate}", initialDelay = 1000)
    public void cleanupAuthSessions() throws UserNotFoundException {
        List<AuthSessionModel> authSessions = authSessionRepository.findAll();

        for(AuthSessionModel s : authSessions){
            if((System.currentTimeMillis() - s.getCreated_at().getTime()) >= Long.parseLong(expirationDeltaString)){
                deleteSession(s.getUsername(), s.getToken());
            }
        }
    }

    private AuthSessionModel getByUsernameAndToken(String username, String token) throws UserNotFoundException {
        List<AuthSessionModel> result = authSessionRepository.findByUsername(username)
                .stream().filter(x -> x.getToken().equals(token))
                .collect(Collectors.toList());
        if(result.size() == 0) throw new UserNotFoundException(String.format("User %s does not exist", username));
        return result.get(0);
    }

    public boolean checkUsernameExists(String username){
        List<UserModel> resultUsername = userRepository.findByUsername(username);

        return resultUsername.size() != 0;
    }

    public boolean checkEmailExists(String email){
        List<UserModel> resultEmail = userRepository.findByEmail(email);

        return resultEmail.size() != 0;
    }
}
