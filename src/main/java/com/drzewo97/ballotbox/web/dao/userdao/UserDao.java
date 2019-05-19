package com.drzewo97.ballotbox.web.dao.userdao;

import com.drzewo97.ballotbox.model.role.Role;

import java.util.Set;

public class UserDao {

    private Long id;

    private String username;

    private Set<Role> roles;

    public UserDao() {
    }

    public UserDao(Long id, String username, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
