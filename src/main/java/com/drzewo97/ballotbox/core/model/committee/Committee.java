package com.drzewo97.ballotbox.core.model.committee;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Committee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany
	Set<Candidate> members;
	
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
	
	public Set<Candidate> getMembers() {
		return members;
	}
	
	public void setMembers(Set<Candidate> members) {
		this.members = members;
	}
}
