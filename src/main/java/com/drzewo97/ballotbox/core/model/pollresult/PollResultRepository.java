package com.drzewo97.ballotbox.core.model.pollresult;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollResultRepository extends CrudRepository<PollResult, Long> {
	Optional<PollResult> findByPoll(Poll poll);
}
