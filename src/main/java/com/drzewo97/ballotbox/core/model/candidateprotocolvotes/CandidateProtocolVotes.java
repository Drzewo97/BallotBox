package com.drzewo97.ballotbox.core.model.candidateprotocolvotes;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;

import javax.persistence.*;

@Entity
public class CandidateProtocolVotes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Candidate candidate;
	
	private Integer votesCount;
	
	public CandidateProtocolVotes(Candidate candidate) {
		this.candidate = candidate;
	}
	
	public CandidateProtocolVotes() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Candidate getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	
	public Integer getVotesCount() {
		return votesCount;
	}
	
	public void setVotesCount(Integer votesCount) {
		this.votesCount = votesCount;
	}
}
