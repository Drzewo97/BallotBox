package com.drzewo97.ballotbox.core.service.calculation.candidateresult;

import com.drzewo97.ballotbox.core.model.candidateresult.CandidateResult;
import com.drzewo97.ballotbox.core.model.vote.Vote;

import java.util.Set;

public interface CandidateResultsCalculationService {
	Set<CandidateResult> calculateResults(Set<Vote> votes);
}
