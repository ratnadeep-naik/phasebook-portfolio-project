package com.portfolio.phasebook.backend.entity;


import java.sql.Timestamp;

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
public class Friend {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer friendId;

    private Timestamp dateAndTime;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "friend1_id", nullable = false)
    private User friend1Id;

	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "friend2_id", nullable = false)
    private User friend2Id;

	public Friend() {
	}

	public Integer getFriendId() {
		return friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}



	
	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public User getFriend1Id() {
		return friend1Id;
	}

	public void setFriend1Id(User friend1Id) {
		this.friend1Id = friend1Id;
	}

	public User getFriend2Id() {
		return friend2Id;
	}

	public void setFriend2Id(User friend2Id) {
		this.friend2Id = friend2Id;
	}
	
	
	
	



}
