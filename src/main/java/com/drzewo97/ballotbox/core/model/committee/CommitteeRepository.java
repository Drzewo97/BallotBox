package com.drzewo97.ballotbox.core.model.committee;

import org.springframework.data.repository.CrudRepository;

public interface CommitteeRepository extends CrudRepository<Committee, Long> {
	Boolean existsByName(String name);
}
