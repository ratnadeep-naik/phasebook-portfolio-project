package com.portfolio.phasebook.backend.model;
import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

public class CommentModel {

	boolean own;
	Integer commentId;
//	@NotNull
//	Integer postId;
	@NotNull
	int postId;
	String creatorName;
	String creatorEmail;
	String creatorImageUrl;
	Timestamp creationDate;
	@NotNull
    String content;
	Integer pageNumber;

	public CommentModel() {
	}

	public boolean isOwn() {
		return own;
	}

	public void setOwn(boolean own) {
		this.own = own;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
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

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}



}
