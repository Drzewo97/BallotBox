package com.drzewo97.ballotbox.core.model.poll;

import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface PollRepository extends CrudRepository<Poll, Integer> {
    Boolean existsByName(String name);
    Set<Poll> findByCountryOrDistrictOrWard(Country country, District district, Ward ward);
    
    @Query("select p from Poll p where p.id=?1 and (p.country=?2 or p.district=?3 or p.ward=?4)")
    Optional<Poll> findByIdAndCountryOrDistrictOrWard(Integer Id, Country country, District district, Ward ward);
}
