package com.drzewo97.ballotbox.service.userservice;

import com.drzewo97.ballotbox.model.poll.Poll;
import com.drzewo97.ballotbox.model.user.User;
import com.drzewo97.ballotbox.web.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);
    void voted(String username, Poll poll);
    void save(UserDto userDto);
}
