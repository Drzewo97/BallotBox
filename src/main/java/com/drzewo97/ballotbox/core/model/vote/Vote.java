package com.drzewo97.ballotbox.core.model.vote;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.poll.Poll;

import javax.persistence.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

@Entity
public class Vote implements IVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Poll poll;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.PERSIST)
    private Set<VoteInfo> choices;

    public Vote() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    
    @Override
    public Candidate getMostPreferredCandidate() {
        return Collections.min(choices, Comparator.comparing(VoteInfo::getPreferenceNumber)).getCandidate();
    }
}
