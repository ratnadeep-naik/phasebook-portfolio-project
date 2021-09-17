package com.portfolio.phasebook.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.Likes;
import com.portfolio.phasebook.backend.entity.Post;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.repository.LikesRepository;

@Service
@Transactional
public class LikesService {
	
	@Autowired
	LikesRepository likesRepository;
	
	public Optional<Likes> getLikesById(Integer  id) {
		return likesRepository.findById(id);
	}
	
	public Likes saveLikes(Likes likes) {
		return likesRepository.save(likes);
	}
	
	public Likes updateLikes(Likes likes) {
		return likesRepository.save(likes);
	}
	
	public void deleteLikes(Likes likes) {
		likesRepository.delete(likes);
	}

	
	public Long countLikesByPost(Post postId) {
		return likesRepository.countByPostId(postId);
	}

	public Long countLikesOrDisLikesByPost(Post postId, boolean likeOrDisLike) {
		return likesRepository.countByPostIdAndLiked(postId, likeOrDisLike);
	}


	public boolean isLiked(User userId, Post postId) {
		return likesRepository.existsByUserIdAndPostId(userId, postId);
	}
	
	public boolean userLiked(User userId, Post postId) {
		List<Likes> likes= likesRepository.findByUserIdAndPostId(userId, postId);
		if(!likes.isEmpty()) {
			return likes.get(0).isLiked();
		}
		return false;
	}
	
	public boolean userDisLiked(User userId, Post postId) {
		List<Likes> likes= likesRepository.findByUserIdAndPostId(userId, postId);
		if(!likes.isEmpty()) {
			return !likes.get(0).isLiked();
		}
		return false;
	}

	public long deleteByUserIdAndPostId(User userId, Post postId) {
		return likesRepository.deleteByUserIdAndPostId(userId, postId);
	}
	
	public Likes findByUserIdAndPostId(User userId, Post postId) {
		List<Likes> likes= likesRepository.findByUserIdAndPostId(userId, postId);
		if(likes!=null) {
			return likes.get(0);
		}
		return null;
	}
	
	
	public void saveAllLikes(List<Likes> likes) {
		likesRepository.saveAll(likes);
	}


}
