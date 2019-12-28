package com.drzewo97.ballotbox.core.beans;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.service.calculation.candidateresult.CandidateResultsCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.candidateresult.InstantRunoffCandidateResultsCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.candidateresult.VotesNumberResultsCalculationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CoreBeans {
	
	@Bean
	@Scope(value = "prototype")
	public CandidateResultsCalculationService getCandidateResultsCalculationService(Poll poll){
		CandidateResultsCalculationService returner = null;
		
		switch (poll.getPollType()){
			case WINNER_TAKES_ALL:
			case TWO_ROUND:
				returner = new VotesNumberResultsCalculationService();
				break;
			case INSTANT_RUNOFF_VOTING:
				returner = new InstantRunoffCandidateResultsCalculationService(poll.getCandidatesCount(), poll.getVotingMode());
				break;
		}
		
		return returner;
	}
}
