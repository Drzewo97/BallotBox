package com.drzewo97.ballotbox.core.model.election;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends CrudRepository<Election, Integer> {
	Boolean existsByName(String name);
}
