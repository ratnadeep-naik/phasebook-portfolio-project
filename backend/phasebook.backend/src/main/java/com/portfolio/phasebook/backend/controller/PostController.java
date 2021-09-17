package com.portfolio.phasebook.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.phasebook.backend.entity.Post;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.resources.ResourcesService;
import com.portfolio.phasebook.backend.service.CommentsService;
import com.portfolio.phasebook.backend.service.PostService;
import com.portfolio.phasebook.backend.service.UserService;

@RestController
@RequestMapping(value = "post")
public class PostController {

	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentsService commentsService;

	
	@Autowired
	ResourcesService resourcesService;
	
	//create a post
	@PostMapping(value = "createPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	private Post createPost(
			@Valid @ModelAttribute Post post, 
			@RequestParam(value = "image", required = false) MultipartFile imageFile, 
			@RequestParam(value = "video", required = false) MultipartFile videoFile,
			HttpServletResponse response,
			Principal principal) throws IOException, InterruptedException {
		
		User user = userService.getUsersByEmail(principal.getName());
		post.setUserId(user);
		
		if(imageFile!=null) {
			post.setImageUrl(resourcesService.createResource(imageFile, "jpg")) ;
		}
		
		if(videoFile!=null) {
			post.setVideoUrl(resourcesService.createResource(videoFile, "mp4"));
		}
		
		if(post.getAudience()=="public") {
			post.setAudience("public");
			
		}else if(post.getAudience()=="personal") {
			post.setAudience("personal");
			
		}else if(post.getAudience()=="friend") {
			post.setAudience("friend");
		}else {
			post.setAudience("public");
			
		}
		

		return postService.savePost(post);
	}

	//update a post audience
	@PostMapping(value = "updatePostAudience")
	private Post updatePostAudience(
			@RequestParam(value = "postId", required = true) Integer id, 
			@RequestParam(value = "audience", required = true) String audience,
			HttpServletResponse response
			) throws IOException
	{
			Optional<Post> post = postService.getPostById(id);

			post.get().setAudience(audience);
			return postService.updatePost(post.get());
	}

	//delete a post
	@PostMapping(value = "deletePost")
	private void deletePost(@RequestParam(value = "postId", required = true) Integer id) {
		postService.deletePost(postService.getPostById(id).get());
	}
}
