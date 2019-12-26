package com.drzewo97.ballotbox.core.model.pollresult;

import com.drzewo97.ballotbox.core.model.candidateresult.CandidateResult;
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
	
	@OneToMany
	private Set<CandidateResult> candidateResults;
	
	private Boolean resolved;
	
	@OneToMany
	private Set<CandidateResult> winners;
	
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
	
	public Set<CandidateResult> getCandidateResults() {
		return candidateResults;
	}
	
	public void setCandidateResults(Set<CandidateResult> candidateResults) {
		this.candidateResults = candidateResults;
	}
	
	public Boolean getResolved() {
		return resolved;
	}
	
	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}
	
	public Set<CandidateResult> getWinners() {
		return winners;
	}
	
	public void setWinners(Set<CandidateResult> winners) {
		this.winners = winners;
	}
	
	public Integer getVotesCasted() {
		return votesCasted;
	}
	
	public void setVotesCasted(Integer votesCasted) {
		this.votesCasted = votesCasted;
	}
}
