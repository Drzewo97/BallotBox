package com.drzewo97.ballotbox.core.model.district;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends CrudRepository<District, Integer> {
	Boolean existsByNameAndCountry_Name(String name, String countryName);
}
