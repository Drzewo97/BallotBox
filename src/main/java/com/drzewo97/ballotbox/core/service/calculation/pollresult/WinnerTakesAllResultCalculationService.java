package com.drzewo97.ballotbox.core.service.calculation.pollresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.pollresult.PollResult;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WinnerTakesAllResultCalculationService implements PollResultCalculationService {
	
	@Override
	public PollResult calculateResult(Set<Candidate> candidateResults) {
		PollResult pollResult = new PollResult();
		
		// number of votes
		pollResult.setVotesCasted(candidateResults.stream().mapToInt(Candidate::getVotesPlaced).sum());
		
		// TODO: optimization - one method
		// candidate with most number of votes
		Candidate winner = Collections.max(candidateResults);
		Long winnerCount = candidateResults.stream().filter(r->r.getVotesPlaced().equals(winner.getVotesPlaced())).count();
		
		// if we've got a winner
		if(winnerCount == 1){
			pollResult.setResolved(true);
			
			Set<Candidate> winners = Collections.singleton(winner);
			pollResult.setWinners(winners);
			
			return pollResult;
		}
		
		// no winner -> equal number of votes
		pollResult.setResolved(false);
		pollResult.setWinners(new HashSet<>());
		
		return pollResult;
	}
}
