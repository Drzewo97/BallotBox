package com.drzewo97.ballotbox.core.model.committee;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CommitteeRepository extends CrudRepository<Committee, Long> {
	Boolean existsByName(String name);
	Set<Committee> findAllByCommitteeAdminIsNull();
	Set<Committee> findAllByCommitteeAdminUsername(String username);
}
