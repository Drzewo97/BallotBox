package com.drzewo97.ballotbox.core.model.role;

import javax.persistence.*;

/**
 * Represents role of user necessary for authorization
 * @see com.drzewo97.ballotbox.core.model.user.User
 */
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
