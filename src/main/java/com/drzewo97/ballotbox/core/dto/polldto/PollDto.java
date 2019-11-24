package com.drzewo97.ballotbox.core.dto.polldto;

import com.drzewo97.ballotbox.core.constraint.datesorder.DatesOrder;
import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.poll.PollType;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @see com.drzewo97.ballotbox.core.model.poll.Poll
 */
@DatesOrder(before = "openFrom", after = "openUntil", message = "From must be before Until")
//@CollectionSize(size = "candidatesCount", collection = "candidates")
public class PollDto {
    @NotEmpty
    private String name;

    private String description;
    
    private Set<Country> countries;
    
    private Set<District> districts;
    
    private Set<Ward> wards;

    @NotNull
    @Positive
    private Integer candidatesCount;

    /**
     * Just to simplify, and not deal with enum in template form
     * if true - VotingMode.EXACTLY, false - VotingMode.AT_MOST
     * @see com.drzewo97.ballotbox.core.model.poll.VotingMode
     */
    @NotNull
    private Boolean exactly;
    
    private PollType pollType;

    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime openFrom;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime openUntil;

    public PollDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCandidatesCount() {
        return candidatesCount;
    }

    public void setCandidatesCount(Integer candidatesCount) {
        this.candidatesCount = candidatesCount;
    }

    public Boolean getExactly() {
        return exactly;
    }

    public void setExactly(Boolean exactly) {
        this.exactly = exactly;
    }

    public LocalDateTime getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(LocalDateTime openFrom) {
        this.openFrom = openFrom;
    }

    public LocalDateTime getOpenUntil() {
        return openUntil;
    }

    public void setOpenUntil(LocalDateTime openUntil) {
        this.openUntil = openUntil;
    }
    
    public Set<Country> getCountries() {
        return countries;
    }
    
    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
    
    public Set<District> getDistricts() {
        return districts;
    }
    
    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }
    
    public Set<Ward> getWards() {
        return wards;
    }
    
    public void setWards(Set<Ward> wards) {
        this.wards = wards;
    }
    
    public PollType getPollType() {
        return pollType;
    }
    
    public void setPollType(PollType pollType) {
        this.pollType = pollType;
    }
}
