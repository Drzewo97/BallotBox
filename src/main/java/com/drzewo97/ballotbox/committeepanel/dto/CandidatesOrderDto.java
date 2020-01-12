package com.drzewo97.ballotbox.committeepanel.dto;

import com.drzewo97.ballotbox.core.model.committeecandidateorder.CommitteeCandidateOrder;

import java.util.List;

/**
 * just a wrapper
 */
public class CandidatesOrderDto {
	List<CommitteeCandidateOrder> candidatesOrder;
	
	public List<CommitteeCandidateOrder> getCandidatesOrder() {
		return candidatesOrder;
	}
	
	public void setCandidatesOrder(List<CommitteeCandidateOrder> candidatesOrder) {
		this.candidatesOrder = candidatesOrder;
	}
}
