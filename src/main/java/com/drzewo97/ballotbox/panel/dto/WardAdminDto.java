package com.drzewo97.ballotbox.panel.dto;

import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.ward.Ward;

public class WardAdminDto {
	
	private Ward ward;
	
	private User user;
	
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
