package com.drzewo97.ballotbox.model.vote;

import com.drzewo97.ballotbox.model.choice.Choice;
import com.drzewo97.ballotbox.model.poll.Poll;

import javax.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Poll poll;

    @ManyToOne
    private Choice choice;

    public Vote() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }
}
