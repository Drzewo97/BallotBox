package com.drzewo97.ballotbox.core.model.candidate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Long> {
    Boolean existsByName(String name);
}
