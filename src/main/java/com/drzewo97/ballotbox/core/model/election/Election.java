package com.drzewo97.ballotbox.core.model.election;

import com.drzewo97.ballotbox.core.model.poll.Poll;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Election {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@OneToMany(mappedBy = "election")
	private Set<Poll> polls;
	
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
	
	public Set<Poll> getPolls() {
		return polls;
	}
	
	public void setPolls(Set<Poll> polls) {
		this.polls = polls;
	}
}
