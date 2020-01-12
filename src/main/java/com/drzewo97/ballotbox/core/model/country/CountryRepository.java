package com.drzewo97.ballotbox.core.model.country;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {
	Boolean existsByName(String name);
	Optional<Country> findByName(String name);
}
