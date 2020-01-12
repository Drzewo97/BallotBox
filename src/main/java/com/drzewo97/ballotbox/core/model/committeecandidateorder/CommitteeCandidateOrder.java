package com.drzewo97.ballotbox.core.model.committeecandidateorder;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.committee.Committee;

import javax.persistence.*;

@Entity
public class CommitteeCandidateOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Candidate candidate;
	
	@ManyToOne
	private Committee committee;
	
	private Integer candidateOrder;
	
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
	
	public Committee getCommittee() {
		return committee;
	}
	
	public void setCommittee(Committee committee) {
		this.committee = committee;
	}
	
	public Integer getCandidateOrder() {
		return candidateOrder;
	}
	
	public void setCandidateOrder(Integer candidateOrder) {
		this.candidateOrder = candidateOrder;
	}
}
