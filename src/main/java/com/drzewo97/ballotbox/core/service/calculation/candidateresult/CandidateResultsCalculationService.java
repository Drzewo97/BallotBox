package com.drzewo97.ballotbox.core.service.calculation.candidateresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocol;
import com.drzewo97.ballotbox.core.model.vote.IVote;

import java.util.Collection;
import java.util.Set;

public interface CandidateResultsCalculationService {
	/**
	 *
	 * @param votes
	 * @param wardProtocols empty if poll is not eligible for protocol votes count
	 * @return
	 */
	Set<Candidate> calculateResults(Set<? extends IVote> votes, Collection<CandidatesVotesCountWardProtocol> wardProtocols);
}
