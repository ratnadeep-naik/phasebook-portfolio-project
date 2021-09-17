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
public class FriendRequest {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer friendRequestId;

    private Timestamp dateAndTime;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "friend1_request_id", nullable = false)
    private User friend1RequestId;

	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "friend2_request_id", nullable = false)
    private User friend2RequestId;
	
	private boolean readed;

	public FriendRequest() {
	}

	public Integer getFriendRequestId() {
		return friendRequestId;
	}

	public void setFriendRequestId(Integer friendRequestId) {
		this.friendRequestId = friendRequestId;
	}



	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public User getFriend1RequestId() {
		return friend1RequestId;
	}

	public void setFriend1RequestId(User friend1RequestId) {
		this.friend1RequestId = friend1RequestId;
	}

	public User getFriend2RequestId() {
		return friend2RequestId;
	}

	public void setFriend2RequestId(User friend2RequestId) {
		this.friend2RequestId = friend2RequestId;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}



}
