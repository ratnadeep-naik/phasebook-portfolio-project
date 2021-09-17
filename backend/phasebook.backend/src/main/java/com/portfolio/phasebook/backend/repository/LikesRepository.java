package com.portfolio.phasebook.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.phasebook.backend.entity.Likes;
import com.portfolio.phasebook.backend.entity.Post;
import com.portfolio.phasebook.backend.entity.User;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
//what about pagination
//crud operations on a post
	
	List<Likes> findByUserId(User user);
	
	List<Likes> findByUserIdAndPostId(User userId, Post postId);
	
	long countByPostId(Post postId);
	
	long countByPostIdAndLiked(Post postId, boolean trueOrFalse);
	

	boolean existsByUserIdAndPostId(User userId, Post postId);

	Long deleteByUserIdAndPostId(User userId, Post postId);

	//create a post
	
	//update a post audience

	//delete a post
	
	//get a post by post id

	//get all posts by pagination
	
	//get posts by creator user id by pagination


	

	


}
