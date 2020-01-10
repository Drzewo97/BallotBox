package com.drzewo97.ballotbox.core.model.candidateprotocolvotes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateProtocolVotesRepository extends CrudRepository<CandidateProtocolVotes, Long> {
}
