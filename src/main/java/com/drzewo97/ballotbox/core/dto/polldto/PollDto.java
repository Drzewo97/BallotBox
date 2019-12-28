package com.drzewo97.ballotbox.core.dto.polldto;

import com.drzewo97.ballotbox.core.constraint.datesorder.DatesOrder;
import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.poll.PollScope;
import com.drzewo97.ballotbox.core.model.poll.PollType;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * @see com.drzewo97.ballotbox.core.model.poll.Poll
 */
@DatesOrder(before = "openFrom", after = "openUntil", message = "From must be before Until")
//@CollectionSize(size = "candidatesCount", collection = "candidates")
public class PollDto {
    @NotEmpty
    private String name;

    private String description;
    
    private Country country;
    
    private District district;
    
    private Ward ward;

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
    
    private PollScope pollScope;

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
    
    public Country getCountry() {
        return country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }
    
    public District getDistrict() {
        return district;
    }
    
    public void setDistrict(District district) {
        this.district = district;
    }
    
    public Ward getWard() {
        return ward;
    }
    
    public void setWard(Ward ward) {
        this.ward = ward;
    }
    
    public PollType getPollType() {
        return pollType;
    }
    
    public void setPollType(PollType pollType) {
        this.pollType = pollType;
    }
    
    public PollScope getPollScope() {
        return pollScope;
    }
    
    public void setPollScope(PollScope pollScope) {
        this.pollScope = pollScope;
    }
}
