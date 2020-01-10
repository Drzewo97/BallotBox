package com.drzewo97.ballotbox.core.service.calculation.pollresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.pollresult.PollResult;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TwoRoundResultCalculationService implements PollResultCalculationService {
	
	@Override
	public PollResult calculateResult(Set<Candidate> candidateResults) {
		PollResult pollResult = new PollResult();
		
		// number of votes
		pollResult.setVotesCasted(candidateResults.stream().mapToInt(Candidate::getVotesPlaced).sum());
		
		// candidate with most number of votes
		Candidate winner = Collections.max(candidateResults);
		
		// if we've got a winner
		if(winner.getVotesPlaced() > (pollResult.getVotesCasted()*0.5)){
			pollResult.setResolved(true);
			
			Set<Candidate> winners = Collections.singleton(winner);
			pollResult.setWinners(winners);
			
			return pollResult;
		}
		
		// no winner -> next round
		pollResult.setResolved(false);
		pollResult.setWinners(new HashSet<>());
		
		return pollResult;
	}
	
}
