package com.drzewo97.ballotbox.core.dto.userdto;

import com.drzewo97.ballotbox.core.constraint.fieldmatch.FieldMatch;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * User DTO used for registration
 * @see com.drzewo97.ballotbox.core.model.user.User
 */
@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
public class UserDto {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    //TODO: email validation
    private String email;

    public UserDto() {
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
