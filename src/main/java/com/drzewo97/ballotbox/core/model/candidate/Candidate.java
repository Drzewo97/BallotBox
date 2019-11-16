package com.drzewo97.ballotbox.core.model.candidate;

import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @ManyToOne
    private User user;

    @ManyToOne
    private Poll poll;
    
    /**
     * district of candidate - if null it's not available in poll of district
     */
    @ManyToOne
    @Nullable
    private District district;
    
    /**
     * country of candidate - if null it's not available in poll of country
     */
    @ManyToOne
    @Nullable
    private Country country;
    
    /**
     * ward of candidate - if null it's not available in poll of ward
     */
    @ManyToOne
    @Nullable
    private Ward ward;

    public Candidate() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Poll getPoll() {
        return poll;
    }
    
    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    
    @Nullable
    public District getDistrict() {
        return district;
    }
    
    public void setDistrict(@Nullable District district) {
        this.district = district;
    }
    
    @Nullable
    public Country getCountry() {
        return country;
    }
    
    public void setCountry(@Nullable Country country) {
        this.country = country;
    }
    
    @Nullable
    public Ward getWard() {
        return ward;
    }
    
    public void setWard(@Nullable Ward ward) {
        this.ward = ward;
    }
}
