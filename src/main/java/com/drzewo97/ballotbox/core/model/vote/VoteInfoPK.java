package com.drzewo97.ballotbox.core.model.vote;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoteInfoPK implements Serializable {
	
	@Column(name = "vote_id")
	private Integer voteId;
	
	@Column(name = "candidate_id")
	private Integer candidateId;
	
	public Integer getVoteId() {
		return voteId;
	}
	
	public void setVoteId(Integer voteId) {
		this.voteId = voteId;
	}
	
	public Integer getCandidateId() {
		return candidateId;
	}
	
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
}
