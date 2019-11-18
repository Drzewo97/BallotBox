package com.drzewo97.ballotbox.core.model.vote;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoteInfoPK implements Serializable {
	
	@Column(name = "vote_id")
	private Long voteId;
	
	@Column(name = "candidate_id")
	private Long candidateId;
	
	public Long getVoteId() {
		return voteId;
	}
	
	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}
	
	public Long getCandidateId() {
		return candidateId;
	}
	
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
}
