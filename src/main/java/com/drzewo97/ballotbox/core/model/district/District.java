package com.drzewo97.ballotbox.core.model.district;

import com.drzewo97.ballotbox.core.model.choice.Choice;
import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.ward.Ward;

import javax.persistence.*;
import java.util.Set;

@Entity
public class District {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "district")
	private Set<Ward> wards;
	
	@ManyToOne
	private Country country;
	
	@OneToMany(mappedBy = "district")
	private Set<Choice> choices;
	
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
	
	public Set<Ward> getWards() {
		return wards;
	}
	
	public void setWards(Set<Ward> wards) {
		this.wards = wards;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public Set<Choice> getChoices() {
		return choices;
	}
	
	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}
}
