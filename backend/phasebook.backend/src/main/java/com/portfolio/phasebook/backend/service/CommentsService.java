package com.portfolio.phasebook.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.Comments;
import com.portfolio.phasebook.backend.entity.Post;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.repository.CommentsRepository;

@Service
@Transactional
public class CommentsService {
	

	@Autowired
	CommentsRepository commentsRepository;
	
	public Optional<Comments> getCommentById(Integer id) {
		return commentsRepository.findById(id);
	}

	public Comments saveComment(Comments comments) {
		
		return commentsRepository.save(comments);
	}
	
	public Comments updateComment(Comments comments) {
		return commentsRepository.save(comments);
	}

	public void deleteComment(Comments comment) {
		commentsRepository.delete(comment);
	}
	
	
	public Long countCommentsByPost(Post postId) {
		return commentsRepository.countByPostId(postId);
	}

	
	public boolean isCommented(User userId, Post postId) {
		return commentsRepository.existsByUserIdAndPostId(userId, postId);
	}
	
	public List<Comments> findCommentsByPostId(Post postId, Integer pageNumber) {
		return commentsRepository.findByPostId(postId, PageRequest.of(pageNumber, 5, Sort.by("commentId").descending())).toList();
	}
	
	public void saveAllComments(List<Comments> comments) {
		 commentsRepository.saveAll(comments);
	}
}
