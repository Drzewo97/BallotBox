package com.drzewo97.ballotbox.core.model.poll;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.election.Election;
import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.vote.Vote;
import com.drzewo97.ballotbox.core.model.ward.Ward;

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

    @Enumerated(EnumType.STRING)
    private VotingMode votingMode;
    
    @Enumerated(EnumType.STRING)
    private PollScope pollScope;

    private LocalDateTime openFrom;

    private LocalDateTime openUntil;

    @ManyToMany(mappedBy = "pollsVoted")
    private Set<User> voters;
    
    @ManyToOne
    private Election election;
    
    @ManyToOne
    private Country country;
    
    @ManyToOne
    private District district;
    
    @ManyToOne
    private Ward ward;

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
    
    public PollScope getPollScope() {
        return pollScope;
    }
    
    public void setPollScope(PollScope pollScope) {
        this.pollScope = pollScope;
    }
    
    public Country getCountry() {
        return country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }
    
    public District getDistrict() {
        return district;
    }
    
    public void setDistrict(District district) {
        this.district = district;
    }
    
    public Ward getWard() {
        return ward;
    }
    
    public void setWard(Ward ward) {
        this.ward = ward;
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
    
    /**
     * Is eligible for sending protocol with votes count for each candidate
     * @return
     */
    public Boolean isProtocolVotesCountEligible(){
        switch (this.pollType){
            case WINNER_TAKES_ALL:
            case TWO_ROUND:
                return true;
            case INSTANT_RUNOFF_VOTING:
            default:
                return false;
        }
    }
}
