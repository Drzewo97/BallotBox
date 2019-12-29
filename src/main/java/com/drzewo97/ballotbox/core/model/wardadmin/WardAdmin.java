package com.drzewo97.ballotbox.core.model.wardadmin;

import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.ward.Ward;

import javax.persistence.*;

@Entity
public class WardAdmin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Ward ward;
	
	@ManyToOne
	private User user;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Ward getWard() {
		return ward;
	}
	
	public void setWard(Ward ward) {
		this.ward = ward;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
