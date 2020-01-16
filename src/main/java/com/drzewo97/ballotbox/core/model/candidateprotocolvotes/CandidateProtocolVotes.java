package com.drzewo97.ballotbox.core.model.candidateprotocolvotes;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocol;

import javax.persistence.*;

@Entity
public class CandidateProtocolVotes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Candidate candidate;
	
	private Integer votesCount;
	
	@ManyToOne
	private CandidatesVotesCountWardProtocol candidatesVotesCountWardProtocol;
	
	public CandidateProtocolVotes(Candidate candidate) {
		this.candidate = candidate;
	}
	
	public CandidateProtocolVotes() {
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
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
	
	public CandidatesVotesCountWardProtocol getCandidatesVotesCountWardProtocol() {
		return candidatesVotesCountWardProtocol;
	}
	
	public void setCandidatesVotesCountWardProtocol(CandidatesVotesCountWardProtocol candidatesVotesCountWardProtocol) {
		this.candidatesVotesCountWardProtocol = candidatesVotesCountWardProtocol;
	}
}
