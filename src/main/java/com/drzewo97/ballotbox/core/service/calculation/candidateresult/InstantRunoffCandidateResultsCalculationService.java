package com.drzewo97.ballotbox.core.service.calculation.candidateresult;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidateresult.CandidateResult;
import com.drzewo97.ballotbox.core.model.poll.VotingMode;
import com.drzewo97.ballotbox.core.model.vote.IVote;
import com.drzewo97.ballotbox.core.model.vote.Vote;
import com.drzewo97.ballotbox.core.model.vote.VoteInfo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

public class InstantRunoffCandidateResultsCalculationService extends VotesNumberResultsCalculationService {
	
	private Integer candidatesCount;
	
	private VotingMode votingMode;
	
	public InstantRunoffCandidateResultsCalculationService(Integer candidatesCount, VotingMode votingMode) {
		this.candidatesCount = candidatesCount;
		this.votingMode = votingMode;
	}
	
	@Override
	public Set<CandidateResult> calculateResults(Set<? extends IVote> votes) {
		return instantRunoffCandidateResults(votes);
	}
	
	protected Set<CandidateResult> instantRunoffCandidateResults(Set<? extends IVote> votes){
		// map votes to Instant Runoff votes
		Set<IRVoteReplacement> irVotes = votes.stream().map(v ->  new IRVoteReplacement((Vote)v)).collect(Collectors.toSet());
		
		// calculate results for first preference of voters
		Set<CandidateResult> candidateResults = votesNumberCandidateResults(irVotes);
		
		// while winner didn't get majority
		while(Collections.max(candidateResults).getVotesPlaced() <= (candidateResults.stream().mapToInt(CandidateResult::getVotesPlaced).sum()*0.5)){
			// find looser
			Candidate looser = Collections.min(candidateResults).getCandidate();
			
			// eliminate looser from votes
			irVotes.forEach(v -> v.eliminatePreferredCandidateIf(looser));
			
			// recalculate
			candidateResults = votesNumberCandidateResults(irVotes);
		}
		
		return candidateResults;
	}
}

class IRVoteReplacement implements IVote {
	
	private Queue<Candidate> preferenceCandidates;
	
	public IRVoteReplacement(Vote vote){
		PriorityQueue<VoteInfo> preferenceVotes = new PriorityQueue<>(Comparator.comparing(VoteInfo::getPreferenceNumber));
		vote.getChoices().forEach(v -> preferenceVotes.add(v));
		
		preferenceCandidates = new LinkedList<>();
		while (preferenceVotes.peek() != null){
			preferenceCandidates.add(preferenceVotes.poll().getCandidate());
		}
	}
	
	public void eliminatePreferredCandidateIf(Candidate candidate){
		preferenceCandidates.remove(candidate);
	}
	
	@Override
	public Candidate getMostPreferredCandidate() {
		return preferenceCandidates.peek();
	}
}
