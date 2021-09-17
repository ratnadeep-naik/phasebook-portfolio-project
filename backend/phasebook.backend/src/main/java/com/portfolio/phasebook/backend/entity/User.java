package com.portfolio.phasebook.backend.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity(name="user")
public class User {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(name="STUDENT_NAME", length=50, nullable=false, unique=false)
//	@Column(name="user_id", nullable=false)
	private Integer userId;

	@Email
	@NotNull
	private String email;

	@NotNull
	private String password;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;

	private String photoUrl;
	
	private String address;
	
	private String phone;
	
	private String gender;
	
//	@Past
	private Date dob;
	
	private String jobInfo;
	
	private String academicInfo;
	
	private String personalInfo;
	
	private Timestamp lastAppearance;
	
	
	@JsonManagedReference
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

	@JsonManagedReference
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Likes> likes;

	@JsonManagedReference
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comments> comments;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "friend1Id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Friend> friend1;

	@JsonManagedReference
	@OneToMany(mappedBy = "friend2Id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Friend> friend2;

	@JsonManagedReference
	@OneToMany(mappedBy = "friend1ChatId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FriendChat> friend1Chat;

	@JsonManagedReference
	@OneToMany(mappedBy = "friend2ChatId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FriendChat> friend2Chat;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "friend1RequestId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FriendRequest> friend1Request;

	@JsonManagedReference
	@OneToMany(mappedBy = "friend2RequestId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FriendRequest> friend2Request;
	
										/* group related */
	@JsonManagedReference
	@OneToMany(mappedBy = "creatorId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Group> createdGroup;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "memberUserId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupMember> memberOfGroup;
	

	@JsonManagedReference
	@OneToMany(mappedBy = "requestedUserId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupJoinRequest> groupsInterested;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "memberUserGroupChatCreatorId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupChat> chatInGroup;
	
	

	//how will you have relations
	
	

	public User() {
		
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Likes> getLikes() {
		return likes;
	}

	public void setLikes(List<Likes> likes) {
		this.likes = likes;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public List<Friend> getFriend1() {
		return friend1;
	}

	public void setFriend1(List<Friend> friend1) {
		this.friend1 = friend1;
	}

	public List<Friend> getFriend2() {
		return friend2;
	}

	public void setFriend2(List<Friend> friend2) {
		this.friend2 = friend2;
	}

	public List<FriendChat> getFriend1Chat() {
		return friend1Chat;
	}

	public void setFriend1Chat(List<FriendChat> friend1Chat) {
		this.friend1Chat = friend1Chat;
	}

	public List<FriendChat> getFriend2Chat() {
		return friend2Chat;
	}

	public void setFriend2Chat(List<FriendChat> friend2Chat) {
		this.friend2Chat = friend2Chat;
	}

	public List<FriendRequest> getFriend1Request() {
		return friend1Request;
	}

	public void setFriend1Request(List<FriendRequest> friend1Request) {
		this.friend1Request = friend1Request;
	}

	public List<FriendRequest> getFriend2Request() {
		return friend2Request;
	}

	public void setFriend2Request(List<FriendRequest> friend2Request) {
		this.friend2Request = friend2Request;
	}

	public List<Group> getCreatedGroup() {
		return createdGroup;
	}

	public void setCreatedGroup(List<Group> createdGroup) {
		this.createdGroup = createdGroup;
	}

	public List<GroupMember> getMemberOfGroup() {
		return memberOfGroup;
	}

	public void setMemberOfGroup(List<GroupMember> memberOfGroup) {
		this.memberOfGroup = memberOfGroup;
	}

	public List<GroupJoinRequest> getGroupsInterested() {
		return groupsInterested;
	}

	public void setGroupsInterested(List<GroupJoinRequest> groupsInterested) {
		this.groupsInterested = groupsInterested;
	}

	public List<GroupChat> getChatInGroup() {
		return chatInGroup;
	}

	public void setChatInGroup(List<GroupChat> chatInGroup) {
		this.chatInGroup = chatInGroup;
	}

	public Timestamp getLastAppearance() {
		return lastAppearance;
	}

	public void setLastAppearance(Timestamp lastAppearance) {
		this.lastAppearance = lastAppearance;
	}

	


}
