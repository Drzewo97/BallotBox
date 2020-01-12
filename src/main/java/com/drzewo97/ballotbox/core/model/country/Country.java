package com.drzewo97.ballotbox.core.model.country;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.district.District;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Country {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@OneToMany(mappedBy = "country")
	private Set<District> districts;
	
	@OneToMany(mappedBy = "country")
	private Set<Candidate> candidates;
	
	public Country(){
	}
	
	public Country(String name) {
		this.name = name;
		this.districts = new HashSet<>();
		this.candidates = new HashSet<>();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<District> getDistricts() {
		return districts;
	}
	
	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}
	
	public Set<Candidate> getCandidates() {
		return candidates;
	}
	
	public void setCandidates(Set<Candidate> candidates) {
		this.candidates = candidates;
	}
}
