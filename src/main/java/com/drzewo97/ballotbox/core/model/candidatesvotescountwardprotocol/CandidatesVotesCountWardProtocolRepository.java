package com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidatesVotesCountWardProtocolRepository extends CrudRepository<CandidatesVotesCountWardProtocol, Long> {
	Optional<CandidatesVotesCountWardProtocol> findByWardAndPoll(Ward ward, Poll poll);
}
