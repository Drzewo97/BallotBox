package com.drzewo97.ballotbox.core.model.poll;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.election.Election;
import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.vote.Vote;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User creator;

    private String name;

    private String description;

    /**
     * Set of possible choices for voter
     */
    @OneToMany(mappedBy = "poll")
    private Set<Candidate> candidates;

    /**
     * Set of all casted votes
     */
    @OneToMany(mappedBy = "poll")
    private Set<Vote> votes;

    /**
     * Number of possible choices for a user
     */
    private Integer candidatesCount;
    
    @Enumerated(EnumType.STRING)
    private PollType pollType;

    // is included in voting method?
    @Enumerated(EnumType.STRING)
    private VotingMode votingMode;

    private LocalDateTime openFrom;

    private LocalDateTime openUntil;

    @ManyToMany(mappedBy = "pollsVoted")
    private Set<User> voters;
    
    @ManyToOne
    private Election election;

    public Poll() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Integer getCandidatesCount() {
        return candidatesCount;
    }

    public void setCandidatesCount(Integer candidatesCount) {
        this.candidatesCount = candidatesCount;
    }

    public VotingMode getVotingMode() {
        return votingMode;
    }

    public void setVotingMode(VotingMode votingMode) {
        this.votingMode = votingMode;
    }

    public LocalDateTime getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(LocalDateTime openFrom) {
        this.openFrom = openFrom;
    }

    public LocalDateTime getOpenUntil() {
        return openUntil;
    }

    public void setOpenUntil(LocalDateTime openUntil) {
        this.openUntil = openUntil;
    }

    public Set<User> getVoters() {
        return voters;
    }

    public void setVoters(Set<User> voters) {
        this.voters = voters;
    }

    public void appendVote(Vote vote){
        this.votes.add(vote);
    }
    
    public PollType getPollType() {
        return pollType;
    }
    
    public void setPollType(PollType pollType) {
        this.pollType = pollType;
    }
    
    public Boolean getActive(){
        return LocalDateTime.now().isBefore(getOpenUntil()) && LocalDateTime.now().isAfter(getOpenFrom());
    }
    
    public Election getElection() {
        return election;
    }
    
    public void setElection(Election election) {
        this.election = election;
    }
    
    public Integer getVotesCastedCount(){
        return voters.size();
    }
    
    /**
     * Check if user of username has already voted on this poll
     * @param username voter to be checked
     * @return true if has voted already, false otherwise
     */
    public Boolean hasVoted(String username){
        for(User u : voters){
            if(username.equals(u.getUsername())){
                return true;
            }
        }

        return false;
    }
}
