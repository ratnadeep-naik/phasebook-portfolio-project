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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name="likes")
public class Likes {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer likeId;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post postId;
    
    private Timestamp dateAndTime;

	@NotNull
	private boolean liked;

	

	public Likes() {
	}



	public Integer getLikeId() {
		return likeId;
	}



	public void setLikeId(Integer likeId) {
		this.likeId = likeId;
	}



	public User getUserId() {
		return userId;
	}



	public void setUserId(User userId) {
		this.userId = userId;
	}



	public Post getPostId() {
		return postId;
	}



	public void setPostId(Post postId) {
		this.postId = postId;
	}






	public Timestamp getDateAndTime() {
		return dateAndTime;
	}



	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}



	public boolean isLiked() {
		return liked;
	}



	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	


}
