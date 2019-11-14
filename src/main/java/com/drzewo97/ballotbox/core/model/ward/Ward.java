package com.drzewo97.ballotbox.core.model.ward;

import com.drzewo97.ballotbox.core.model.choice.Choice;
import com.drzewo97.ballotbox.core.model.district.District;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Ward {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne
	private District district;
	
	@OneToMany(mappedBy = "ward")
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
	
	public District getDistrict() {
		return district;
	}
	
	public void setDistrict(District district) {
		this.district = district;
	}
	
	public Set<Choice> getChoices() {
		return choices;
	}
	
	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}
}
