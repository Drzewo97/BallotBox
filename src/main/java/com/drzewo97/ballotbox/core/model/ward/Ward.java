package com.drzewo97.ballotbox.core.model.ward;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.wardadmin.WardAdmin;

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
	private Set<Candidate> candidates;
	
	@OneToOne
	private WardAdmin wardAdmin;
	
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
	
	public Set<Candidate> getCandidates() {
		return candidates;
	}
	
	public void setCandidates(Set<Candidate> candidates) {
		this.candidates = candidates;
	}
	
	public WardAdmin getWardAdmin() {
		return wardAdmin;
	}
	
	public void setWardAdmin(WardAdmin wardAdmin) {
		this.wardAdmin = wardAdmin;
	}
}
