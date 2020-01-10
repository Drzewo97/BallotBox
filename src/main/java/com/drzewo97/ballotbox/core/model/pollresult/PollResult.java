package com.drzewo97.ballotbox.core.model.pollresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.poll.Poll;

import javax.persistence.*;
import java.util.Set;

@Entity
public class PollResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Poll poll;
	
	private Integer votesCasted;
	
	private Boolean resolved;
	
	@OneToMany
	private Set<Candidate> winners;
	
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
	
	public Boolean getResolved() {
		return resolved;
	}
	
	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}
	
	public Set<Candidate> getWinners() {
		return winners;
	}
	
	public void setWinners(Set<Candidate> winners) {
		this.winners = winners;
	}
	
	public Integer getVotesCasted() {
		return votesCasted;
	}
	
	public void setVotesCasted(Integer votesCasted) {
		this.votesCasted = votesCasted;
	}
}
