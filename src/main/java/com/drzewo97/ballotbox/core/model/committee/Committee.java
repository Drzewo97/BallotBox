package com.drzewo97.ballotbox.core.model.committee;

import com.drzewo97.ballotbox.core.model.aspirant.Aspirant;
import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.committeecandidateorder.CommitteeCandidateOrder;
import com.drzewo97.ballotbox.core.model.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Committee implements Aspirant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@OneToMany(mappedBy = "committee")
	private Set<Candidate> members;
	
	@OneToOne
	private User committeeAdmin;
	
	@OneToMany(mappedBy = "committee")
	private Set<CommitteeCandidateOrder> candidateOrders;
	
	private Integer votesPlaced;
	
	private Integer seatsGranted;
	
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
	
	public Set<CommitteeCandidateOrder> getCandidateOrders() {
		return candidateOrders;
	}
	
	public void setCandidateOrders(Set<CommitteeCandidateOrder> candidateOrders) {
		this.candidateOrders = candidateOrders;
	}
	
	public void setVotesPlaced(Integer votesPlaced) {
		this.votesPlaced = votesPlaced;
	}
	
	public Integer getSeatsGranted() {
		return seatsGranted;
	}
	
	public void setSeatsGranted(Integer seatsGranted) {
		this.seatsGranted = seatsGranted;
	}
	
	@Override
	public Integer getVotesPlaced() {
		return votesPlaced;
	}
	
	@Override
	public int compareTo(Aspirant o) {
		return this.getVotesPlaced().compareTo(o.getVotesPlaced());
	}
}
