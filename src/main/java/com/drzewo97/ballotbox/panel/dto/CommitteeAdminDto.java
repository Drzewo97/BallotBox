package com.drzewo97.ballotbox.panel.dto;

import com.drzewo97.ballotbox.core.model.committee.Committee;
import com.drzewo97.ballotbox.core.model.user.User;

public class CommitteeAdminDto {
	
	private Committee committee;
	
	private User user;
	
	public Committee getCommittee() {
		return committee;
	}
	
	public void setCommittee(Committee committee) {
		this.committee = committee;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
