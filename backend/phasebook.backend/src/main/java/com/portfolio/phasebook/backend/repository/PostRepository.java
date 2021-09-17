package com.portfolio.phasebook.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.phasebook.backend.entity.Post;
import com.portfolio.phasebook.backend.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	Page<Post> findByUserId(User user, org.springframework.data.domain.Pageable pageable);

	Page<Post> findByUserIdAndAudienceIgnoreCaseOrAudienceIgnoreCase(User user,String puplic, String friend, org.springframework.data.domain.Pageable pageable);
	
	Page<Post> findByUserIdAndAudienceIgnoreCase(User user,String puplic, org.springframework.data.domain.Pageable pageable);
	
	//create a post
	
	//update a post audience

	//delete a post
	
	//get a post by post id

	//get posts by creator user id



	

	


}
