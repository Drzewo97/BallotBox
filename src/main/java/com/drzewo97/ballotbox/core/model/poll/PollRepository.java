package com.drzewo97.ballotbox.core.model.poll;

import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PollRepository extends CrudRepository<Poll, Long> {
    Boolean existsByName(String name);
    Set<Poll> findByCountryOrDistrictOrWard(Country country, District district, Ward ward);
}
