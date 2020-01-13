package com.drzewo97.ballotbox.core.service.calculation.candidateresult;

import com.drzewo97.ballotbox.core.model.aspirant.Aspirant;
import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidateprotocolvotes.CandidateProtocolVotes;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocol;
import com.drzewo97.ballotbox.core.model.committee.Committee;
import com.drzewo97.ballotbox.core.model.vote.IVote;

import java.util.*;

public class DhondtCandidateResultsCalculationService implements CandidateResultsCalculationService {

	private Integer winningCandidatesCount;

	public DhondtCandidateResultsCalculationService(Integer winningCandidatesCount){
		this.winningCandidatesCount = winningCandidatesCount;
	}

	@Override
	public Set<? extends Aspirant> calculateResults(Set<? extends IVote> votes, Collection<CandidatesVotesCountWardProtocol> wardProtocols) {
		// Collect all votes for each candidate
		Set<Committee> committees = new HashSet<>();
		for(IVote vote : votes){
			// only one choice allowed for this type of poll
			Candidate candidate = vote.getMostPreferredCandidate();
			if(candidate == null){
				continue;
			}
			Committee committee = candidate.getCommittee();
			if(committee == null){
				continue;
			}

			committee.setVotesPlaced(committee.getVotesPlaced() + 1);
			candidate.setVotesPlaced(candidate.getVotesPlaced() + 1);
			committees.add(committee);
		}

		if(!wardProtocols.isEmpty()){
			// if we've got some votes from wards
			for(CandidatesVotesCountWardProtocol protocol : wardProtocols){
				//for each ward
				for(CandidateProtocolVotes candidateProtocolVotes : protocol.getCandidateProtocolVotes()){
					// add number of votes for every candidate in this ward
					Candidate candidate = candidateProtocolVotes.getCandidate();
					Committee committee = candidate.getCommittee();
					//TODO: default value 0 instead of null
					committee.setVotesPlaced(committee.getVotesPlaced() + candidateProtocolVotes.getVotesCount());
					candidate.setVotesPlaced(candidate.getVotesPlaced() + candidateProtocolVotes.getVotesCount());
					committees.add(committee);
				}
			}
		}

		// committee with most number of votes - we look for its Dhondtindex position in ending condition of loop
		Committee winnerCommittee = Collections.max(committees);

		// List with list of indexes
		List<DhondtIndex> committeesIndexes = new ArrayList<>();

		// iterator number for index calculation
		Integer i = 0;

		// index of maximum index of current iteration in all indexes
		int maxIndexIndex = 0;
		do{
			// another iteration
			i++;

			// hold largest index of this iteration
			DhondtIndex winnerDhondtIndex = new DhondtIndex();
			for(Committee committee : committees){
				// calculate index
				DhondtIndex index = new DhondtIndex(committee, i, committee.getVotesPlaced()/i);

				// if index is of winning committee
				if(committee.equals(winnerCommittee)){
					winnerDhondtIndex = index;
				}

				// add to indexes list
				committeesIndexes.add(index);
			}

			Collections.sort(committeesIndexes, Collections.reverseOrder());

			// look for maximum index of current iteration in list -> if index of it is larger than winningCandidatesCount we can stop loop
			maxIndexIndex = committeesIndexes.indexOf(winnerDhondtIndex);
		}while (maxIndexIndex < winningCandidatesCount);

		// calculate number of granted seats
		for(i = 0; i < winningCandidatesCount; i++){
			Committee committee = committeesIndexes.get(i).getCommittee();
			committee.setSeatsGranted(committee.getSeatsGranted() + 1);
		}

		Set<Candidate> winners = new HashSet<>();

		for(Committee committee : committees){
			for(i = 1; i <= committee.getSeatsGranted() ; i++){
				Integer finalI = i;
				//TODO: assumption that order is set correctly
				Candidate winner = committee.getCandidateOrders().stream().filter(c -> c.getCandidateOrder().equals(finalI)).findFirst().get().getCandidate();
				winners.add(winner);
			}
		}

		return winners;
	}
}

class DhondtIndex implements Comparable<DhondtIndex> {
	private Committee committee;
	private Integer iterator;
	private Integer indexValue;

	public DhondtIndex(Committee committee, Integer iterator, Integer indexValue) {
		this.committee = committee;
		this.iterator = iterator;
		this.indexValue = indexValue;
	}

	public DhondtIndex() {
	}

	public Committee getCommittee() {
		return committee;
	}

	public void setCommittee(Committee committee) {
		this.committee = committee;
	}

	public Integer getIterator() {
		return iterator;
	}

	public void setIterator(Integer iterator) {
		this.iterator = iterator;
	}

	public Integer getIndexValue() {
		return indexValue;
	}

	public void setIndexValue(Integer indexValue) {
		this.indexValue = indexValue;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DhondtIndex that = (DhondtIndex) o;
		return Objects.equals(committee, that.committee) && Objects.equals(iterator, that.iterator);
	}

	@Override
	public int hashCode() {
		return Objects.hash(committee, iterator, indexValue);
	}

	@Override
	public int compareTo(DhondtIndex o) {
		return this.indexValue.compareTo(o.indexValue);
	}
}