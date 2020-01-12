package com.drzewo97.ballotbox.core.service.pollservice;

import com.drzewo97.ballotbox.core.dto.polldto.PollDto;
import com.drzewo97.ballotbox.core.model.poll.Poll;

public interface PollService {
    /**
     * check if user has already voted in poll
     * @param username username of the user
     * @param pollId id of poll to be checked
     * @return true only if user has voted, 0 in any other case
     * @deprecated use only when you don't need to persist Poll entity,
     * otherwise use method provided in Poll class (only because I say so)
     * @see Poll
     */
    @Deprecated
    Boolean hasVoted(String username, Integer pollId);

    /**
     * Save pollDto as poll
     * @param pollDto VALID Dto of Poll object
     * @param creatorUsername username of creator
     */
    void save(PollDto pollDto, String creatorUsername);

    /**
     * Check if Poll exists by name provided
     * @param name name of the poll that has to be checked
     * @return true if name exists in data source, false otherwise
     */
    Boolean existsByName(String name);
}
