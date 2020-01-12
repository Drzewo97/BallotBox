package com.drzewo97.ballotbox.core.model.committee;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Committee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany
	private Set<Candidate> members;
	
	@OneToOne
	private User committeeAdmin;
	
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
	
	public User getCommitteeAdmin() {
		return committeeAdmin;
	}
	
	public void setCommitteeAdmin(User committeeAdmin) {
		this.committeeAdmin = committeeAdmin;
	}
}
