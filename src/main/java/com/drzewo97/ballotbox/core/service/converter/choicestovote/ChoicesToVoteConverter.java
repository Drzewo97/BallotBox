package com.drzewo97.ballotbox.core.service.converter.choicestovote;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.vote.Vote;
import com.drzewo97.ballotbox.core.model.vote.VoteInfo;
import com.drzewo97.ballotbox.core.service.converter.Converter;
import com.drzewo97.ballotbox.votingmachine.dto.VoteDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ChoicesToVoteConverter implements Converter<Set<Map.Entry<Candidate, VoteDto>>, Vote> {
	@Override
	public Vote convert(Set<Map.Entry<Candidate, VoteDto>> candidates) {
		if(candidates.size() == 0){
			throw new RuntimeException("Nothing to convert!");
		}
		
		Poll poll = candidates.stream().findFirst().get().getKey().getPoll();
		
		// check if all candidates belong to one poll
		if(!candidates.stream().allMatch(candidate -> candidate.getKey().getPoll().getId().equals(poll.getId()))){
			throw new RuntimeException("Candidates do not belong to one poll!");
		}
		
		Vote vote = new Vote();
		vote.setPoll(poll);
		
		Set<VoteInfo> voteInfos = new HashSet<>();
		if(poll.isPreferenceVoting()) {
			for (Map.Entry<Candidate, VoteDto> candidate : candidates) {
				if(candidate.getValue().getPreferenceNumber() == null || candidate.getValue().getPreferenceNumber() < 1){
					throw new RuntimeException("Want to save a vote on candidate that was not chosen");
				}
				voteInfos.add(new VoteInfo(vote, candidate.getKey(), candidate.getValue().getPreferenceNumber()));
			}
		}
		else{
			for (Map.Entry<Candidate, VoteDto> candidate : candidates) {
				if(!candidate.getValue().getChecked()){
					throw new RuntimeException("Want to save a vote on candidate that was not checked");
				}
				voteInfos.add(new VoteInfo(vote, candidate.getKey(), 1));
			}
		}
		
		vote.setChoices(voteInfos);
		
		return vote;
	}
}
