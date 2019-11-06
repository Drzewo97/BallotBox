package com.drzewo97.ballotbox.core.model.poll;

import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {
    Boolean existsByName(String name);
}
