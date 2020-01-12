package com.drzewo97.ballotbox.core.model.candidate;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Integer> {
    Boolean existsByName(String name);
    Boolean existsByPollAndUser(Poll poll, User user);
}
