package com.drzewo97.ballotbox.core.model.ward;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface WardRepository extends CrudRepository<Ward, Long> {
	Boolean existsByNameAndDistrict_Name(String name, String districtName);
	Set<Ward> findAllByWardAdminIsNull();
	Set<Ward> findAllByWardAdminUsername(String username);
}
