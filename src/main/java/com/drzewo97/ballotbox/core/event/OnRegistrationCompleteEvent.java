package com.drzewo97.ballotbox.core.event;

import com.drzewo97.ballotbox.core.model.user.User;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
	
	private User user;
	private String appUrl;
	
	public OnRegistrationCompleteEvent(User user, String appUrl) {
		super(user);
		
		this.user = user;
		this.appUrl = appUrl;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getAppUrl() {
		return appUrl;
	}
	
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
}
