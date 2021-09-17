package com.portfolio.phasebook.backend.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class AuthRequest {

    private String userName;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
	private String password;
    @NotNull
    @Email
    private String email;
    private String photoUrl;
	private String address;
	private String phone;
	private String gender;
	private Date dob;	
	private String jobInfo;
	private String academicInfo;
	private String personalInfo;
	private Timestamp lastAppearance;


    public AuthRequest() {

	}



	public AuthRequest(String userName, String password, String email, String firstName, String lastName,
			String photoUrl, String address, String phone, String gender, Date dob, String jobInfo, String academicInfo,
			String personalInfo, Timestamp lastAppearance) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.photoUrl = photoUrl;
		this.address = address;
		this.phone = phone;
		this.gender = gender;
		this.dob = dob;
		this.jobInfo = jobInfo;
		this.academicInfo = academicInfo;
		this.personalInfo = personalInfo;
		this.lastAppearance = lastAppearance;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getPhotoUrl() {
		return photoUrl;
	}



	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public Date getDob() {
		return dob;
	}



	public void setDob(Date dob) {
		this.dob = dob;
	}



	public String getJobInfo() {
		return jobInfo;
	}



	public void setJobInfo(String jobInfo) {
		this.jobInfo = jobInfo;
	}



	public String getAcademicInfo() {
		return academicInfo;
	}



	public void setAcademicInfo(String academicInfo) {
		this.academicInfo = academicInfo;
	}



	public String getPersonalInfo() {
		return personalInfo;
	}



	public void setPersonalInfo(String personalInfo) {
		this.personalInfo = personalInfo;
	}



	public Timestamp getLastAppearance() {
		return lastAppearance;
	}



	public void setLastAppearance(Timestamp lastAppearance) {
		this.lastAppearance = lastAppearance;
	}



	@Override
	public String toString() {
		return "AuthRequest [userName=" + userName + ", password=" + password + "]";
	}
    
    
    
    
}
