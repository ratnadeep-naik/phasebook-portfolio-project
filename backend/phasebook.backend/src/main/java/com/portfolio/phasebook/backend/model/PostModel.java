package com.portfolio.phasebook.backend.model;
import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

public class PostModel {

	boolean own;
	String email;		//other interested users email whose post we want to see
	Integer postId;
	String creatorName;
	Timestamp creationDate;
	String creatorEmail;
	
	String creatorImageUrl;
	String audience;
    String textMessage;
	String imageUrl;
	String videoUrl;
	Long likes;
	Long comments;
	Long disLikes;
	Integer pageNumber;
	boolean liked;
	boolean disLiked;
	public boolean isDisLiked() {
		return disLiked;
	}

	public void setDisLiked(boolean disLiked) {
		this.disLiked = disLiked;
	}

	boolean commented;

	public PostModel() {
	}

	public boolean isOwn() {
		return own;
	}
	
	
	

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public String getCreatorImageUrl() {
		return creatorImageUrl;
	}

	public void setCreatorImageUrl(String creatorImageUrl) {
		this.creatorImageUrl = creatorImageUrl;
	}

	public Long getDisLikes() {
		return disLikes;
	}

	public void setDisLikes(Long disLikes) {
		this.disLikes = disLikes;
	}

	public void setOwn(boolean own) {
		this.own = own;
	}
	


	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp timestamp) {
		this.creationDate = timestamp;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public String getTextMessage() {
		return textMessage;
	}
	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Long getLikes() {
		return likes;
	}
	public void setLikes(Long long1) {
		this.likes = long1;
	}
	public Long getComments() {
		return comments;
	}
	public void setComments(Long comments) {
		this.comments = comments;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public boolean isCommented() {
		return commented;
	}

	public void setCommented(boolean commented) {
		this.commented = commented;
	}


	



}
