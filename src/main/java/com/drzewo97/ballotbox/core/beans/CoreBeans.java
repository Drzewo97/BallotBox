package com.drzewo97.ballotbox.core.beans;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.service.calculation.candidateresult.CandidateResultsCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.candidateresult.DhondtCandidateResultsCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.candidateresult.InstantRunoffCandidateResultsCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.candidateresult.VotesNumberResultsCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.pollresult.DhondtWinnersPollResultCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.pollresult.PollResultCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.pollresult.TwoRoundResultCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.pollresult.WinnerTakesAllResultCalculationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CoreBeans {
	
	@Bean
	@Scope(value = "prototype")
	public CandidateResultsCalculationService getCandidateResultsCalculationService(Poll poll){
		switch (poll.getPollType()){
			case WINNER_TAKES_ALL:
			case TWO_ROUND:
				return new VotesNumberResultsCalculationService();
			case INSTANT_RUNOFF_VOTING:
				return new InstantRunoffCandidateResultsCalculationService(poll.getCandidatesCount(), poll.getVotingMode());
			case DHONDT:
			default:
				return new DhondtCandidateResultsCalculationService(poll.getWinningCandidatesCount());
		}
	}
	
	@Bean
	@Scope(value = "prototype")
	public PollResultCalculationService getPollResultCalculationService(Poll poll){
		switch (poll.getPollType()){
			case INSTANT_RUNOFF_VOTING:
			case WINNER_TAKES_ALL:
				return new WinnerTakesAllResultCalculationService();
			case TWO_ROUND:
				return new TwoRoundResultCalculationService();
			case DHONDT:
			default:
				return new DhondtWinnersPollResultCalculationService(poll);
		}
	}
}
