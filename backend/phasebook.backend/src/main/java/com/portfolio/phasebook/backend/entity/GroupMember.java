package com.portfolio.phasebook.backend.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class GroupMember {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer groupMemberId;

    private Timestamp dateAndTime;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_user_id", nullable = false)
    private User memberUserId;

	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "selected_group_id", nullable = false)
    private Group selectedGroupId;

	public GroupMember() {
	}

	public Integer getGroupMemberId() {
		return groupMemberId;
	}

	public void setGroupMemberId(Integer groupMemberId) {
		this.groupMemberId = groupMemberId;
	}



	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public User getMemberUserId() {
		return memberUserId;
	}

	public void setMemberUserId(User memberUserId) {
		this.memberUserId = memberUserId;
	}

	public Group getSelectedGroupId() {
		return selectedGroupId;
	}

	public void setSelectedGroupId(Group selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}
	
	
	
	



}
