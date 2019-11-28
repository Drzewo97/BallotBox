package com.drzewo97.ballotbox.core.model.vote;

import com.drzewo97.ballotbox.core.model.poll.Poll;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Poll poll;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.PERSIST)
    private Set<VoteInfo> choices;

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
    
    public Set<VoteInfo> getChoices() {
        return choices;
    }
    
    public void setChoices(Set<VoteInfo> choices) {
        this.choices = choices;
    }
}
