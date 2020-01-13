package com.drzewo97.ballotbox.core.service.calculation.pollresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.pollresult.PollResult;

import java.util.HashSet;
import java.util.Set;

public class DhondtWinnersPollResultCalculationService implements PollResultCalculationService {
	
	private Poll poll;
	
	public DhondtWinnersPollResultCalculationService(Poll poll) {
		this.poll = poll;
	}
	
	@Override
	public PollResult calculateResult(Set<Candidate> candidateResults) {
		PollResult pollResult = new PollResult();
		pollResult.setResolved(true);
		pollResult.setVotesCasted(poll.getCandidates().stream().mapToInt(Candidate::getVotesPlaced).sum());
		pollResult.setPoll(poll);
		
		Set<Candidate> winners = new HashSet<>(candidateResults);
		pollResult.setWinners(winners);
		
		return pollResult;
	}
}
