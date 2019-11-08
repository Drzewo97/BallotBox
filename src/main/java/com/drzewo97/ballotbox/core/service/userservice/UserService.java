package com.drzewo97.ballotbox.core.service.userservice;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.web.dto.userdto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);
    void voted(String username, Poll poll);
    void save(UserDto userDto);
    List<User> findAll();
    void toggleModeratorRole(Long userId);
    Optional<User> findById(Long id);
    Boolean existsById(Long id);
}
