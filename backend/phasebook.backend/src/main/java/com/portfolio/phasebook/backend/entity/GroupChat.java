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
public class GroupChat {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer groupChatId;

    private Timestamp dateAndTime;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_user_group_chat_creator_id", nullable = false)
    private User memberUserGroupChatCreatorId;

	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_receiving_group_id", nullable = false)
    private Group chatReceivingGroupId;
	
	private String chatMessage;

	private String chatImageUrl;
	
	private String chatVideoUrl;

	public GroupChat() {
	}

	public Integer getGroupChatId() {
		return groupChatId;
	}

	public void setGroupChatId(Integer groupChatId) {
		this.groupChatId = groupChatId;
	}



	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public User getMemberUserGroupChatCreatorId() {
		return memberUserGroupChatCreatorId;
	}

	public void setMemberUserGroupChatCreatorId(User memberUserGroupChatCreatorId) {
		this.memberUserGroupChatCreatorId = memberUserGroupChatCreatorId;
	}

	public Group getChatReceivingGroupId() {
		return chatReceivingGroupId;
	}

	public void setChatReceivingGroupId(Group chatReceivingGroupId) {
		this.chatReceivingGroupId = chatReceivingGroupId;
	}

	public String getChatMessage() {
		return chatMessage;
	}

	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}

	public String getChatImageUrl() {
		return chatImageUrl;
	}

	public void setChatImageUrl(String chatImageUrl) {
		this.chatImageUrl = chatImageUrl;
	}

	public String getChatVideoUrl() {
		return chatVideoUrl;
	}

	public void setChatVideoUrl(String chatVideoUrl) {
		this.chatVideoUrl = chatVideoUrl;
	}
	
	



}
