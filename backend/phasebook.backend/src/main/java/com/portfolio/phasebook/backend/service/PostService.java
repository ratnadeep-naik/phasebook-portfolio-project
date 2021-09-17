package com.portfolio.phasebook.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.Post;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.repository.PostRepository;

@Service
@Transactional
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	public Optional<Post> getPostById(Integer  id) {
		return postRepository.findById(id);
	}
	
	public Post savePost(Post post) {
		return postRepository.save(post);
	}
	
	public Post updatePost(Post post) {
		return postRepository.save(post);
	}
	
	public void deletePost(Post post) {
		postRepository.delete(post);
	}
	
	public List<Post> findPostsByUser(User user, Integer pageNumber){
		return postRepository.findByUserId(user, PageRequest.of(pageNumber, 20, Sort.by("postId").descending())).toList();
	}
	
	public List<Post> findPostsByUserAndAudience(User user, String audience, Integer pageNumber){
		return postRepository.findByUserIdAndAudienceIgnoreCase(user, audience, PageRequest.of(pageNumber, 20, Sort.by("postId").descending())).toList();
	}
	
	public List<Post> findPostsByUserAndAudience(User user, String audience1, String audience2, Integer pageNumber){
		return postRepository.findByUserIdAndAudienceIgnoreCaseOrAudienceIgnoreCase(user, audience1, audience2, PageRequest.of(pageNumber, 20, Sort.by("postId").descending())).toList();
	}
	
	public List<Post>findAllPostsByPagination(Integer pageNumber){
		return postRepository.findAll(PageRequest.of(pageNumber, 20, Sort.by("postId").descending())).toList();
		
	}
	
	public void saveAllPosts(List<Post> posts) {
		postRepository.saveAll(posts);
	}
	
	



}
