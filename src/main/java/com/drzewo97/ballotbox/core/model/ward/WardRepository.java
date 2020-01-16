package com.drzewo97.ballotbox.core.model.ward;

import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.district.District;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface WardRepository extends CrudRepository<Ward, Integer> {
	Boolean existsByNameAndDistrict_Name(String name, String districtName);
	Set<Ward> findAllByWardAdminIsNull();
	Set<Ward> findAllByWardAdminUsername(String username);
	Set<Ward> findAllByDistrict(District district);
	Set<Ward> findAllByDistrictCountry(Country country);
}
