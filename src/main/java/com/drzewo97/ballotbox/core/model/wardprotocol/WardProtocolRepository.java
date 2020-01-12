package com.drzewo97.ballotbox.core.model.wardprotocol;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WardProtocolRepository extends CrudRepository<WardProtocol, Integer> {
	Optional<WardProtocol> findByWardAndPoll(Ward ward, Poll poll);
}
