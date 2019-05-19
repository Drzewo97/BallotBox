package com.drzewo97.ballotbox.model.vote;

import com.drzewo97.ballotbox.model.choice.Choice;
import com.drzewo97.ballotbox.model.poll.Poll;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Poll poll;

    @ManyToMany
    @JoinTable(
            name = "votes_choices",
            joinColumns = @JoinColumn(
                    name = "vote_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "choice_id", referencedColumnName = "id"))
    private Set<Choice> choice;

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

    public Set<Choice> getChoice() {
        return choice;
    }

    public void setChoice(Set<Choice> choice) {
        this.choice = choice;
    }
}
