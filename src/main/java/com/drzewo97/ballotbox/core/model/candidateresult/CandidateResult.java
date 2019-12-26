package com.drzewo97.ballotbox.core.model.candidateresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;

import javax.persistence.*;

@Entity
public class CandidateResult implements Comparable<CandidateResult> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer place;
	
	private Integer votesPlaced;
	
	@OneToOne
	private Candidate candidate;
	
	public CandidateResult(Integer votesPlaced, Candidate candidate) {
		this.votesPlaced = votesPlaced;
		this.candidate = candidate;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getPlace() {
		return place;
	}
	
	public void setPlace(Integer place) {
		this.place = place;
	}
	
	public Integer getVotesPlaced() {
		return votesPlaced;
	}
	
	public void setVotesPlaced(Integer votesPlaced) {
		this.votesPlaced = votesPlaced;
	}
	
	public Candidate getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	
	@Override
	public int compareTo(CandidateResult o) {
		return this.getVotesPlaced().compareTo(o.getVotesPlaced());
	}
}
