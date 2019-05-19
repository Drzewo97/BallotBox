package com.drzewo97.ballotbox.model.poll;

import com.drzewo97.ballotbox.model.choice.Choice;
import com.drzewo97.ballotbox.model.user.User;
import com.drzewo97.ballotbox.model.vote.Vote;

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
    @ManyToMany
    @JoinTable(
            name = "polls_choices",
            joinColumns = @JoinColumn(
                    name = "poll_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "choice_id", referencedColumnName = "id"))
    private Set<Choice> choices;

    /**
     * Set of all casted votes
     */
    @OneToMany
    private Set<Vote> votes;

    /**
     * Number of possible choices for a user
     */
    private Integer choicesCount;

    @Enumerated(EnumType.STRING)
    private VotingMode votingMode;

    private LocalDateTime openFrom;

    private LocalDateTime openUntil;

    @ManyToMany(mappedBy = "pollsVoted")
    private Set<User> voters;

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

    public Set<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<Choice> choices) {
        this.choices = choices;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Integer getChoicesCount() {
        return choicesCount;
    }

    public void setChoicesCount(Integer choicesCount) {
        this.choicesCount = choicesCount;
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
