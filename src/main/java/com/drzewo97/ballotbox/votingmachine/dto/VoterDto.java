package com.drzewo97.ballotbox.votingmachine.dto;

import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.ward.Ward;

public class VoterDto {
	private String username;
	
	private String password;
	
	private String passwordRepeat;
	
	private String firstName;
	
	private String surname;
	
	private Country country;
	
	private District district;
	
	private Ward ward;
	
	private String citizenId;
	
	private String email;
	
	private String address;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordRepeat() {
		return passwordRepeat;
	}
	
	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public District getDistrict() {
		return district;
	}
	
	public void setDistrict(District district) {
		this.district = district;
	}
	
	public Ward getWard() {
		return ward;
	}
	
	public void setWard(Ward ward) {
		this.ward = ward;
	}
	
	public String getCitizenId() {
		return citizenId;
	}
	
	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
}
