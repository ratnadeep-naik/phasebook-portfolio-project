package com.portfolio.phasebook.backend.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserModel {
	private boolean own;
	@NotNull
	@Email
	private String email;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String password;
	private String photoUrl;
	private String address;
	private String phone;
	private String gender;
	private Date dob;
	private String jobInfo;
	private String academicInfo;
	private String personalInfo;
	private Timestamp lastAppearance;
	private boolean friend;
	private boolean friendRequestSentByThisUser;
	private boolean friendRequestSentToThisUser;
	

	public UserModel() {
	}
	public boolean isOwn() {
		return own;
	}
	public void setOwn(boolean own) {
		this.own = own;
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
	public boolean isFriend() {
		return friend;
	}
	public void setFriend(boolean friend) {
		this.friend = friend;
	}
	public boolean isFriendRequestSentByThisUser() {
		return friendRequestSentByThisUser;
	}
	public void setFriendRequestSentByThisUser(boolean friendRequestSentByThisUser) {
		this.friendRequestSentByThisUser = friendRequestSentByThisUser;
	}
	public boolean isFriendRequestSentToThisUser() {
		return friendRequestSentToThisUser;
	}
	public void setFriendRequestSentToThisUser(boolean friendRequestSentToThisUser) {
		this.friendRequestSentToThisUser = friendRequestSentToThisUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
