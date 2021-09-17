package com.portfolio.phasebook.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.phasebook.backend.entity.Comments;
import com.portfolio.phasebook.backend.entity.Post;
import com.portfolio.phasebook.backend.entity.User;



public interface CommentsRepository extends JpaRepository<Comments, Integer> {
//what about pagination
//crud operations on a post
	
	//create a post
	
	//update a post audience

	//delete a post
	
	//get a post by post id

	//get all posts by pagination
	
	//get posts by creator user id by pagination
	
	long countByPostId(Post postId);
	
	boolean existsByUserIdAndPostId(User userId, Post postId);
	
	Page<Comments> findByPostId(Post postId, org.springframework.data.domain.Pageable pageable);





	

	


}
