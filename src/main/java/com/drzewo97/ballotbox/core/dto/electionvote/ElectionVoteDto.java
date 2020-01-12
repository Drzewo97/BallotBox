package com.drzewo97.ballotbox.core.dto.electionvote;

import com.drzewo97.ballotbox.votingmachine.dto.VoteDto;

import java.util.Map;

public class ElectionVoteDto {

	Map<Integer, VoteDto> candidateIdPreferenceMap;
	
	public Map<Integer, VoteDto> getCandidateIdPreferenceMap() {
		return candidateIdPreferenceMap;
	}
	
	public void setCandidateIdPreferenceMap(Map<Integer, VoteDto> candidateIdPreferenceMap) {
		this.candidateIdPreferenceMap = candidateIdPreferenceMap;
	}
}
