package com.drzewo97.ballotbox.model.user;

import com.drzewo97.ballotbox.model.poll.Poll;
import com.drzewo97.ballotbox.model.role.Role;

import javax.persistence.*;
import java.util.Set;

/**
 * Represents user
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany
    private Set<Poll> pollsCreated;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Poll> getPollsCreated() {
        return pollsCreated;
    }

    public void setPollsCreated(Set<Poll> pollsCreated) {
        this.pollsCreated = pollsCreated;
    }
}