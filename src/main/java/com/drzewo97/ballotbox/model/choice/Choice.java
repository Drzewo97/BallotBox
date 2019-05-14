package com.drzewo97.ballotbox.model.choice;

import com.drzewo97.ballotbox.model.poll.Poll;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private Set<Poll> polls;

    public Choice() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Poll> getPolls() {
        return polls;
    }

    public void setPolls(Set<Poll> polls) {
        this.polls = polls;
    }
}
