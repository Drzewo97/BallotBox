package com.drzewo97.ballotbox.core.service.calculation.candidateresult;

import com.drzewo97.ballotbox.core.model.aspirant.Aspirant;
import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidateprotocolvotes.CandidateProtocolVotes;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocol;
import com.drzewo97.ballotbox.core.model.vote.IVote;

import java.util.*;

public class VotesNumberResultsCalculationService implements CandidateResultsCalculationService {
	
	@Override
	public Set<? extends Aspirant> calculateResults(Set<? extends IVote> votes, Collection<CandidatesVotesCountWardProtocol> wardProtocols) {
		return votesNumberCandidateResults(votes, wardProtocols);
	}
	
	// protected method for improved extensibility
	protected Set<? extends Aspirant> votesNumberCandidateResults(Set<? extends IVote> votes, java.util.Collection<CandidatesVotesCountWardProtocol> wardProtocols){
		
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
		
		if(!wardProtocols.isEmpty()){
		// if we've got some votes from wards
			for(CandidatesVotesCountWardProtocol protocol : wardProtocols){
				//for each ward
				for(CandidateProtocolVotes candidateProtocolVotes : protocol.getCandidateProtocolVotes()){
					// add number of votes for every candidate in this ward
					Candidate candidate = candidateProtocolVotes.getCandidate();
					//TODO: default value 0 instead of null
					candidate.setVotesPlaced(candidate.getVotesPlaced() + candidateProtocolVotes.getVotesCount());
					candidates.add(candidate);
				}
			}
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
