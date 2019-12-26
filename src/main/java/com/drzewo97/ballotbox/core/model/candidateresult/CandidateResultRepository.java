package com.drzewo97.ballotbox.core.model.candidateresult;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateResultRepository extends CrudRepository<CandidateResult, Long> {}
