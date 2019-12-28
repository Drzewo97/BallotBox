package com.drzewo97.ballotbox.core.service.calculation.candidateresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidateresult.CandidateResult;
import com.drzewo97.ballotbox.core.model.vote.IVote;

import java.util.*;

public class VotesNumberResultsCalculationService implements CandidateResultsCalculationService {
	
	@Override
	public Set<CandidateResult> calculateResults(Set<? extends IVote> votes) {
		return votesNumberCandidateResults(votes);
	}
	
	// protected method for improved extensibility
	protected Set<CandidateResult> votesNumberCandidateResults(Set<? extends IVote> votes){
		// Collect all votes for each candidate
		Map<Candidate, Integer> votesCount = new HashMap<>();
		for(IVote vote : votes){
			// only one choice allowed for this type of poll
			Candidate candidate = vote.getMostPreferredCandidate();
			if(candidate == null){
				continue;
			}
			
			votesCount.put(candidate, votesCount.getOrDefault(candidate, 0) + 1);
		}
		
		// construct priority queue of candidates
		PriorityQueue<CandidateResult> results = new PriorityQueue<>(Collections.reverseOrder());
		
		for(Map.Entry<Candidate, Integer> entry : votesCount.entrySet()){
			results.add(new CandidateResult(entry.getValue(), entry.getKey()));
		}
		
		Set<CandidateResult> returner = new HashSet<>();
		
		Integer place = 1;
		while(!results.isEmpty()){
			results.peek().setPlace(place++);
			returner.add(results.poll());
		}
		
		return returner;
	}
}
