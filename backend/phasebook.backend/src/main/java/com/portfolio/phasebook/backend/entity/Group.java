package com.portfolio.phasebook.backend.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user_group")
public class Group {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer groupId;

    private Timestamp dateAndTime;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creatorId;
	
	@NotNull
	private String groupName;
	
	
	@NotNull
	private String groupDescription;
	
	private String groupImageUrl;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "selectedGroupId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupMember> membersInGroup;

	@JsonManagedReference
	@OneToMany(mappedBy = "interestedGroupId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupJoinRequest> interestedInGroup;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "chatReceivingGroupId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupChat> chatsInGroup;

	public Group() {
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}



	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public User getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(User creatorId) {
		this.creatorId = creatorId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getGroupImageUrl() {
		return groupImageUrl;
	}

	public void setGroupImageUrl(String groupImageUrl) {
		this.groupImageUrl = groupImageUrl;
	}

	public List<GroupMember> getMembersInGroup() {
		return membersInGroup;
	}

	public void setMembersInGroup(List<GroupMember> membersInGroup) {
		this.membersInGroup = membersInGroup;
	}

	public List<GroupJoinRequest> getInterestedInGroup() {
		return interestedInGroup;
	}

	public void setInterestedInGroup(List<GroupJoinRequest> interestedInGroup) {
		this.interestedInGroup = interestedInGroup;
	}

	public List<GroupChat> getChatsInGroup() {
		return chatsInGroup;
	}

	public void setChatsInGroup(List<GroupChat> chatsInGroup) {
		this.chatsInGroup = chatsInGroup;
	}
	
	
	
	


}
