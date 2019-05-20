package com.drzewo97.ballotbox.service.userservice;

import com.drzewo97.ballotbox.model.poll.Poll;
import com.drzewo97.ballotbox.model.role.Role;
import com.drzewo97.ballotbox.model.role.RoleRepository;
import com.drzewo97.ballotbox.model.user.User;
import com.drzewo97.ballotbox.model.user.UserRepository;
import com.drzewo97.ballotbox.web.dao.userdao.UserDao;
import com.drzewo97.ballotbox.web.dto.userdto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // check if user is in datasource
        //TODO: mail validation - if exists, deny
        Optional<User> user = userRepository.findByUsername(s);

        // if not throw
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Username not found in datasource");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(),
                getUserAuthorities(user.get()));
    }

    @Override
    public void save(UserDto userDto) {
        // Construct new user based on DTO
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        //TODO: get role prefix (can set it in JdbcUserDetailsManager)
        user.setRoles(Stream.of(new Role("ROLE_USER")).collect(Collectors.toCollection(HashSet::new)));

        // save in datasource
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void voted(String username, Poll poll) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty())
            //TODO: throw?
            return;

        user.get().appendPollsVoted(poll);
        userRepository.save(user.get());
    }

    /**
     * map roles of user to granted authorities required by UserDetails interface
     * @see UserDetails
     */
    private Collection<GrantedAuthority> getUserAuthorities(User user){
        return user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    public List<UserDao> getAllUsers() {
        List<UserDao> userDaos = new ArrayList<>();
        userRepository.findAll().forEach(u -> userDaos.add(new UserDao(u.getId(), u.getUsername(), u.getRoles())));

        return userDaos;
    }

    @Override
    public void toggleModeratorRole(Long userId) {
        // Check if user exists
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return;
        }

        // get moderator role
        Optional<Role> moderatorRole = roleRepository.findByName("ROLE_MODERATOR");
        if(moderatorRole.isEmpty()){
            throw new RuntimeException("There is no moderator role");
        }

        // Toggle moderator role on user
        if(user.get().getRoles().contains(moderatorRole.get())){
            user.get().getRoles().remove(moderatorRole.get());
        }
        else{
            user.get().getRoles().add(moderatorRole.get());
        }

        // save user
        userRepository.save(user.get());
    }
}