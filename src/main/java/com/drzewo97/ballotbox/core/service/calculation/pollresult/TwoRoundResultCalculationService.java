package com.drzewo97.ballotbox.core.service.calculation.pollresult;

import com.drzewo97.ballotbox.core.model.candidateresult.CandidateResult;
import com.drzewo97.ballotbox.core.model.pollresult.PollResult;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TwoRoundResultCalculationService implements PollResultCalculationService {
	
	@Override
	public PollResult calculateResult(Set<CandidateResult> candidateResults) {
		PollResult pollResult = new PollResult();
		pollResult.setCandidateResults(candidateResults);
		
		// number of votes
		pollResult.setVotesCasted(candidateResults.stream().mapToInt(CandidateResult::getVotesPlaced).sum());
		
		// candidate with most number of votes
		CandidateResult winner = Collections.max(candidateResults);
		
		// if we've got a winner
		if(winner.getVotesPlaced() > (pollResult.getVotesCasted()*0.5)){
			pollResult.setResolved(true);
			
			Set<CandidateResult> winners = Collections.singleton(winner);
			pollResult.setWinners(winners);
			
			return pollResult;
		}
		
		// no winner -> next round
		pollResult.setResolved(false);
		pollResult.setWinners(new HashSet<>());
		
		return pollResult;
	}
	
}
