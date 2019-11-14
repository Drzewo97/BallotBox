package com.drzewo97.ballotbox.core.model.country;

import com.drzewo97.ballotbox.core.model.choice.Choice;
import com.drzewo97.ballotbox.core.model.district.District;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Country {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "country")
	private Set<District> districts;
	
	@OneToMany(mappedBy = "country")
	private Set<Choice> choices;
	
	public Country(){
	}
	
	public Country(String name) {
		this.name = name;
		this.districts = new HashSet<>();
		this.choices = new HashSet<>();
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
	
	public Set<District> getDistricts() {
		return districts;
	}
	
	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}
	
	public Set<Choice> getChoices() {
		return choices;
	}
	
	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}
}
