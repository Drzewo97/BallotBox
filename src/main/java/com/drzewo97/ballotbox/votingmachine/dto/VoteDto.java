package com.drzewo97.ballotbox.votingmachine.dto;

public class VoteDto {
	private Boolean checked;
	private Integer preferenceNumber;
	
	public Boolean getChecked() {
		return checked;
	}
	
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public Integer getPreferenceNumber() {
		return preferenceNumber;
	}
	
	public void setPreferenceNumber(Integer preferenceNumber) {
		this.preferenceNumber = preferenceNumber;
	}
}
