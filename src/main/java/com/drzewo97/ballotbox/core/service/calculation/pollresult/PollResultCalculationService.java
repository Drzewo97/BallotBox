package com.drzewo97.ballotbox.core.service.calculation.pollresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.pollresult.PollResult;

import java.util.Set;

public interface PollResultCalculationService {
	PollResult calculateResult(Set<Candidate> candidateResults);
}
