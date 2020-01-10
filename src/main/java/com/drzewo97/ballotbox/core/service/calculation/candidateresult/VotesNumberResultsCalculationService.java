package com.drzewo97.ballotbox.core.service.calculation.candidateresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.vote.IVote;

import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class VotesNumberResultsCalculationService implements CandidateResultsCalculationService {
	
	@Override
	public Set<Candidate> calculateResults(Set<? extends IVote> votes) {
		return votesNumberCandidateResults(votes);
	}
	
	// protected method for improved extensibility
	protected Set<Candidate> votesNumberCandidateResults(Set<? extends IVote> votes){
		
		// Collect all votes for each candidate
		Set<Candidate> candidates = new HashSet<>();
		for(IVote vote : votes){
			// only one choice allowed for this type of poll
			Candidate candidate = vote.getMostPreferredCandidate();
			if(candidate == null){
				continue;
			}
			candidate.setVotesPlaced(candidate.getVotesPlaced() + 1);
			candidates.add(candidate);
		}
		
		// construct priority queue of candidates
		PriorityQueue<Candidate> results = new PriorityQueue<>(Collections.reverseOrder());
		
		for(Candidate candidate : candidates){
			results.add(candidate);
		}
		
		Integer place = 1;
		while(!results.isEmpty()){
			results.poll().setPlace(place++);
		}
		
		return candidates;
	}
}
