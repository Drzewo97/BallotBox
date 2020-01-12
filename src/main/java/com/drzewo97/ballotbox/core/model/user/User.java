package com.drzewo97.ballotbox.core.model.user;

import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.role.Role;
import com.drzewo97.ballotbox.core.model.ward.Ward;

import javax.persistence.*;
import java.util.Set;

/**
 * Represents user
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    
    private String firstName;
    
    private String surname;

    private String password;
    
    private String citizenId;
    
    private String email;
    
    private String address;
    
    private Boolean active;
    
    @ManyToOne
    private Country country;
    
    @ManyToOne
    private District district;
    
    @ManyToOne
    private Ward ward;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany
    private Set<Poll> pollsCreated;

    @ManyToMany
    @JoinTable(
            name = "users_poles_voted",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "poll_id", referencedColumnName = "id"))
    private Set<Poll> pollsVoted;

    public User() {}
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public String getCitizenId() {
        return citizenId;
    }
    
    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
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
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Poll> getPollsCreated() {
        return pollsCreated;
    }

    public void setPollsCreated(Set<Poll> pollsCreated) {
        this.pollsCreated = pollsCreated;
    }

    public Set<Poll> getPollsVoted() {
        return pollsVoted;
    }

    public void setPollsVoted(Set<Poll> pollsVoted) {
        this.pollsVoted = pollsVoted;
    }

    public void appendPollsVoted(Poll poll){
        this.pollsVoted.add(poll);
    }

    public void appendPollsCreated(Poll poll){
        this.pollsCreated.add(poll);
    }
}