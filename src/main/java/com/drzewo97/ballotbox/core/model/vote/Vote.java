package com.drzewo97.ballotbox.core.model.vote;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
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

    @ManyToMany
    @JoinTable(
            name = "votes_candidates",
            joinColumns = @JoinColumn(
                    name = "vote_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "candidate_id", referencedColumnName = "id"))
    private Set<Candidate> candidate;

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

    public Set<Candidate> getCandidate() {
        return candidate;
    }

    public void setCandidate(Set<Candidate> candidate) {
        this.candidate = candidate;
    }
}
