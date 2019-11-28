package com.drzewo97.ballotbox.core.service.userservice;

import com.drzewo97.ballotbox.core.dto.userdto.UserDto;
import com.drzewo97.ballotbox.core.model.role.Role;
import com.drzewo97.ballotbox.core.model.role.RoleRepository;
import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
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
    
    /**
     * map roles of user to granted authorities required by UserDetails interface
     * @see UserDetails
     */
    private Collection<GrantedAuthority> getUserAuthorities(User user) {
        return user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
