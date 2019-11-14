package com.drzewo97.ballotbox.core.model.ward;

import org.springframework.data.repository.CrudRepository;

public interface WardRepository extends CrudRepository<Ward, Long> {
	Boolean existsByNameAndDistrict_Name(String name, String districtName);
}
