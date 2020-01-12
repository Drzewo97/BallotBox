package com.drzewo97.ballotbox.core.model.committeecandidateorder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitteeCandidateOrderRepository extends CrudRepository<CommitteeCandidateOrder, Long> {}
