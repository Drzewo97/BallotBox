package com.drzewo97.ballotbox.core.service.converter.choicestovote;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.vote.Vote;
import com.drzewo97.ballotbox.core.model.vote.VoteInfo;
import com.drzewo97.ballotbox.core.service.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChoicesToVoteConverter implements Converter<Set<Candidate>, Vote> {
	@Override
	public Vote convert(Set<Candidate> candidates) {
		if(candidates.size() == 0){
			throw new RuntimeException("Nothing to convert!");
		}
		
		Poll poll = candidates.stream().findFirst().get().getPoll();
		
		// check if all candidates belong to one poll
		if(!candidates.stream().allMatch(candidate -> candidate.getPoll().getId().equals(poll.getId()))){
			throw new RuntimeException("Candidates do not belong to one poll!");
		}
		
		Vote vote = new Vote();
		vote.setPoll(poll);
		
		Set<VoteInfo> voteInfos = new HashSet<>();
		for(Candidate candidate : candidates){
			//TODO: change preference number
			voteInfos.add(new VoteInfo(vote, candidate, 1));
		}
		
		vote.setChoices(voteInfos);
		
		return vote;
	}
}
