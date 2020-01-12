package com.drzewo97.ballotbox.core.dto.electionvote;

import com.drzewo97.ballotbox.votingmachine.dto.VoteDto;

import java.util.Map;

public class ElectionVoteDto {

	Map<Long, VoteDto> candidateIdPreferenceMap;
	
	public Map<Long, VoteDto> getCandidateIdPreferenceMap() {
		return candidateIdPreferenceMap;
	}
	
	public void setCandidateIdPreferenceMap(Map<Long, VoteDto> candidateIdPreferenceMap) {
		this.candidateIdPreferenceMap = candidateIdPreferenceMap;
	}
}
