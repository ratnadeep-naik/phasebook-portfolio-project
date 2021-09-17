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
public class GroupJoinRequest {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer groupJoinRequestId;

    private Timestamp dateAndTime;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requested_user_id", nullable = false)
    private User requestedUserId;

	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interested_group_id", nullable = false)
    private Group interestedGroupId;

	public GroupJoinRequest() {
	}

	public Integer getGroupJoinRequestId() {
		return groupJoinRequestId;
	}

	public void setGroupJoinRequestId(Integer groupJoinRequestId) {
		this.groupJoinRequestId = groupJoinRequestId;
	}


	
	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public User getRequestedUserId() {
		return requestedUserId;
	}

	public void setRequestedUserId(User requestedUserId) {
		this.requestedUserId = requestedUserId;
	}

	public Group getInterestedGroupId() {
		return interestedGroupId;
	}

	public void setInterestedGroupId(Group interestedGroupId) {
		this.interestedGroupId = interestedGroupId;
	}
	
	
	
	


}
