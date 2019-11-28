package com.drzewo97.ballotbox.core.model.vote;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;

import javax.persistence.*;

@Entity
public class VoteInfo {
	
	@EmbeddedId
	private VoteInfoPK id;
	
	@ManyToOne
	@MapsId("voteId")
	@JoinColumn(name = "vote_id")
	private Vote vote;
	
	@ManyToOne
	@MapsId("candidateId")
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;
	
	/**
	 * whats the preference of voter on this candidate
	 */
	private Integer preferenceNumber;
	
	public VoteInfo() {
	}
	
	public VoteInfo(Vote vote, Candidate candidate, Integer preferenceNumber) {
		this.vote = vote;
		this.candidate = candidate;
		this.preferenceNumber = preferenceNumber;
		this.id = new VoteInfoPK();
		this.id.setCandidateId(candidate.getId());
		this.id.setVoteId(vote.getId());
	}
	
	public VoteInfoPK getId() {
		return id;
	}
	
	public void setId(VoteInfoPK id) {
		this.id = id;
	}
	
	public Vote getVote() {
		return vote;
	}
	
	public void setVote(Vote vote) {
		this.vote = vote;
	}
	
	public Candidate getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	
	public Integer getPreferenceNumber() {
		return preferenceNumber;
	}
	
	public void setPreferenceNumber(Integer preferenceNumber) {
		this.preferenceNumber = preferenceNumber;
	}
}
