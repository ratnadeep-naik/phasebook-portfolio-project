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
public class FriendChat {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer friendChatId;

    private Timestamp dateAndTime;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "friend1_chat_id", nullable = false)
    private User friend1ChatId;

	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "friend2_chat_id", nullable = false)
    private User friend2ChatId;
	
	private String chatMessage;

	private String chatImageUrl;

	private String chatVideoUrl;
	
	private String contentWithContentType1;

	private String contentWithContentType2;


	private boolean readed;

	private int status;

	public FriendChat() {
	}

	public Integer getFriendChatId() {
		return friendChatId;
	}

	public void setFriendChatId(Integer friendChatId) {
		this.friendChatId = friendChatId;
	}



	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public User getFriend1ChatId() {
		return friend1ChatId;
	}

	public void setFriend1ChatId(User friend1ChatId) {
		this.friend1ChatId = friend1ChatId;
	}

	public User getFriend2ChatId() {
		return friend2ChatId;
	}

	public void setFriend2ChatId(User friend2ChatId) {
		this.friend2ChatId = friend2ChatId;
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

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public String getContentWithContentType1() {
		return contentWithContentType1;
	}

	public void setContentWithContentType1(String contentWithContentType1) {
		this.contentWithContentType1 = contentWithContentType1;
	}

	public String getContentWithContentType2() {
		return contentWithContentType2;
	}

	public void setContentWithContentType2(String contentWithContentType2) {
		this.contentWithContentType2 = contentWithContentType2;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	
	
	
	

}
