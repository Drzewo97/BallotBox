package com.drzewo97.ballotbox.service.pollservice;

import com.drzewo97.ballotbox.model.poll.Poll;
import com.drzewo97.ballotbox.web.dto.votedto.VoteDto;

import java.util.Optional;

public interface PollService {
    /**
     * check if user has already voted in poll
     * @param username username of the user
     * @param pollId id of poll to be checked
     * @return true only if user has voted, 0 in any other case
     */
    Boolean hasVoted(String username, Long pollId);

    Optional<Poll> findById(Long id);

    void registerVotes(Poll poll, VoteDto voteDto);
}
