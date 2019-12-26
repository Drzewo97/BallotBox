package com.drzewo97.ballotbox.core.service.calculation.pollresult;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PollResultsCalculationServiceFactory {
	@Autowired
	private ApplicationContext applicationContext;
	
	public PollResultCalculationService getPollResultCalculationService(Poll poll){
		PollResultCalculationService returner = null;
		
		switch (poll.getPollType()){
			case WINNER_TAKES_ALL:
				break;
			case TWO_ROUND:
				returner = applicationContext.getBean(TwoRoundResultCalculationService.class);
				break;
			case INSTANT_RUNOFF_VOTING:
				break;
		}
		
		return returner;
	}
}
