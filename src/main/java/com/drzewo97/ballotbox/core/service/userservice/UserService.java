package com.drzewo97.ballotbox.core.service.userservice;

import com.drzewo97.ballotbox.core.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);
    void save(User user);
    Boolean isRegisterable(User user);
    void createVerificationToken(User user, String token);
    
}
