package com.portfolio.phasebook.backend.controller;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.portfolio.phasebook.backend.config.JwtUtil;
import com.portfolio.phasebook.backend.entity.Comments;
import com.portfolio.phasebook.backend.entity.Friend;
import com.portfolio.phasebook.backend.entity.FriendChat;
import com.portfolio.phasebook.backend.entity.FriendRequest;
import com.portfolio.phasebook.backend.entity.Likes;
import com.portfolio.phasebook.backend.entity.Post;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.model.AuthRequest;
import com.portfolio.phasebook.backend.model.ChatModel;
import com.portfolio.phasebook.backend.model.CommentModel;
import com.portfolio.phasebook.backend.model.Content;
import com.portfolio.phasebook.backend.model.PostModel;
import com.portfolio.phasebook.backend.model.UserModel;
import com.portfolio.phasebook.backend.resources.AWSResourcesService;
import com.portfolio.phasebook.backend.service.CommentsService;
import com.portfolio.phasebook.backend.service.FriendChatService;
import com.portfolio.phasebook.backend.service.FriendRequestService;
import com.portfolio.phasebook.backend.service.FriendService;
import com.portfolio.phasebook.backend.service.GroupChatService;
import com.portfolio.phasebook.backend.service.GroupJoinRequestService;
import com.portfolio.phasebook.backend.service.GroupMemberService;
import com.portfolio.phasebook.backend.service.GroupService;
import com.portfolio.phasebook.backend.service.LikesService;
import com.portfolio.phasebook.backend.service.PostService;
import com.portfolio.phasebook.backend.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "client")
public class WelcomeController {
	
//	@Autowired
//	ResourcesService resourcesService;
	
	@Autowired
	CommentsService commentsService;

	@Autowired
	FriendChatService friendChatService;

	@Autowired
	FriendRequestService friendRequestService;

	@Autowired
	FriendService friendService;

	@Autowired
	GroupChatService groupChatService;

	@Autowired
	GroupJoinRequestService groupJoinRequestService;

	@Autowired
	GroupMemberService groupMemberService;

	@Autowired
	GroupService groupService;

	@Autowired
	LikesService likesService;

	
	@Autowired
	PostService postService;

	@Autowired
	UserService userService;

	@Autowired
	private AWSResourcesService resourceService;

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String welcome(Principal principal) {
        return "Welcome to javatechie !!"+principal.getName();
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        
        String token = jwtUtil.generateToken(authRequest.getEmail());
        System.out.println("token is : "+token);
        return token;
    }
    
    
    @GetMapping("testing/{fileName}")
    public String testingUurpose(@PathVariable String fileName){
    	System.out.println(fileName);
    	return fileName;
    }




    @PostMapping("/signup")
    public String createAccount(@Valid  @RequestBody AuthRequest authRequest, HttpServletResponse response) throws IOException {
    	User isEmailExist  = userService.getUsersByEmail(authRequest.getEmail());
		if(isEmailExist==null) {
			User newUser = new User();
			newUser.setFirstName(authRequest.getFirstName());
			newUser.setLastName(authRequest.getLastName());
			newUser.setEmail(authRequest.getEmail());
			newUser.setPassword(authRequest.getPassword());
			userService.saveUser(newUser);
			return "user created";			
		}
      response.sendError(400, "email already exist");
		return null;
    }
    


    //    create a post in my account

	@PostMapping(value = "uploadPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostModel createPost(
			@Valid @ModelAttribute PostModel post, 
			@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletResponse response,
			Principal principal
    		) throws IOException, InterruptedException {
		
		
		
		String userEmail = principal.getName();
		User user = userService.getUsersByEmail(userEmail);
		Post newPost = new Post();
		newPost.setUserId(user);
		newPost.setAudience(post.getAudience().toUpperCase());
		
		if(post.getTextMessage()!=null) {
			newPost.setTextMessage(post.getTextMessage());
		}
		
		if(file!=null) {
			System.out.println(file.getName());
			System.out.println(file.getContentType());
			System.out.println(file.getOriginalFilename());
			file.getOriginalFilename().endsWith("");
			
			if(file.getContentType().startsWith("video")) {
				String fileName = resourceService.createResource(file, "mp4");
				newPost.setVideoUrl(fileName);
			}else if(file.getContentType().startsWith("image")) {
				String fileName = resourceService.createResource(file, "jpg");
				newPost.setImageUrl(fileName);
			}
		}

			newPost.setDateAndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
			
			postService.savePost(newPost);
			
			PostModel thePost = new PostModel();
			thePost.setOwn(true);
			thePost.setEmail(principal.getName());
			thePost.setPostId(newPost.getPostId());
			thePost.setCreatorName(userService.getUsersByEmail(principal.getName()).getFirstName()+" "+userService.getUsersByEmail(principal.getName()).getLastName());
			thePost.setCreationDate(newPost.getDateAndTime());
			thePost.setAudience(post.getAudience());
			thePost.setTextMessage(newPost.getTextMessage());
			thePost.setImageUrl(newPost.getImageUrl());
			thePost.setVideoUrl(newPost.getVideoUrl());
			thePost.setLikes(0L);
			thePost.setDisLikes(0L);
			thePost.setComments(0L);
			thePost.setLiked(false);
			thePost.setDisLiked(false);
			thePost.setCommented(false);
			thePost.setCreatorImageUrl(newPost.getUserId().getPhotoUrl());
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return thePost;

    }

//    delete a post of my account
	@PostMapping(value = "deletePost")
	public String deletePost(
			@RequestParam(value = "postId", required = true) String postId, 
			HttpServletResponse response,
			Principal principal
			) throws IOException {
		System.out.println(postId);
		Optional<Post> post = postService.getPostById(Integer.parseInt(postId));
		if(post.isPresent()) {
			if(post.get().getUserId().equals(userService.getUsersByEmail(principal.getName()))) {
				if(post.get().getImageUrl()!=null) {
					resourceService.deleteResource(post.get().getImageUrl(), "jpg");
					
				}else if(post.get().getVideoUrl()!=null) {
					resourceService.deleteResource(post.get().getVideoUrl(), "mp4");
				}
				postService.deletePost(post.get());
				return "post successfully deleted";
			}
		}
		response.sendError(400, "post not found in your account");
		return "postId is "+postId;
	}

//    update a post audience in my account
	@PostMapping(value = "updatePost")
	public String updatePost(
			@RequestParam(value = "postId", required = true) String postId, 
			@RequestParam(value = "audience", required = true) String audience, 
			HttpServletResponse response,
			Principal principal
			) throws IOException {
		Optional<Post> post = postService.getPostById(Integer.parseInt(postId));
		if(post.isPresent()) {
			if(post.get().getUserId().equals(userService.getUsersByEmail(principal.getName()))) {
				post.get().setAudience(audience.toUpperCase());
				postService.savePost(post.get());
				return "post successfully updated to "+audience;
			}
		}
		response.sendError(400, "post not found in your account");
		return null;
	}
	
//  read a post of my account, timeline, others account
    @PostMapping("posts")
    public List<PostModel> postProvider(
    		@Valid  @RequestBody PostModel post, 
			HttpServletResponse response,
    		Principal principal
    		) throws ParseException, IOException {
    	System.out.println(post.isOwn());
    	System.out.println(post.getEmail());
    	System.out.println(principal.getName());
    	
    	List<PostModel> posts = new ArrayList<>();

		//send user own posts
    	if(post.isOwn()==true) {
    		User user = userService.getUsersByEmail(principal.getName());
    		List<Post> userPosts = postService.findPostsByUser(user, post.getPageNumber());

    		for(int i=0; i<userPosts.size(); i++) {

    			PostModel thePost = new PostModel();
    			thePost.setOwn(true);
    			thePost.setEmail(user.getEmail());
    			thePost.setPostId(userPosts.get(i).getPostId());
    			thePost.setCreatorName(user.getFirstName()+" "+user.getLastName());
    			thePost.setCreationDate(userPosts.get(i).getDateAndTime());
    			thePost.setAudience(userPosts.get(i).getAudience());
    			thePost.setTextMessage(userPosts.get(i).getTextMessage());
    			thePost.setImageUrl(userPosts.get(i).getImageUrl());
    			thePost.setVideoUrl(userPosts.get(i).getVideoUrl());
    			thePost.setComments(commentsService.countCommentsByPost(userPosts.get(i)));
//    			thePost.setLikes(likesService.countLikesByPost(userPosts.get(i)));
//    			thePost.setDisLikes(0L);
//    			thePost.setDisLiked(false);
//    			thePost.setLiked(likesService.isLiked(user, userPosts.get(i)));
    			thePost.setLikes(likesService.countLikesOrDisLikesByPost(userPosts.get(i), true));
    			thePost.setDisLikes(likesService.countLikesOrDisLikesByPost(userPosts.get(i), false));
    			thePost.setLiked(likesService.userLiked(user, userPosts.get(i)));
    			thePost.setDisLiked(likesService.userDisLiked(user, userPosts.get(i)));
    			thePost.setCommented(commentsService.isCommented(user, userPosts.get(i)));
    			thePost.setCreatorImageUrl(userPosts.get(i).getUserId().getPhotoUrl());
    			thePost.setCreatorEmail(userPosts.get(i).getUserId().getEmail());
    			posts.add(thePost);
    		}
    		
    		return posts;
    	}
    	
		//send the post of users specified by email
    	if(post.getEmail()!="") {
    		User user = userService.getUsersByEmail(post.getEmail());
    		if(user==null) {
    			response.sendError(400, "post not found in your account");
    			return null;
    		}
    		if(friendService.isUserFriend(userService.getUsersByEmail(principal.getName()), user)) {
    			//post with audience of friend and public can be display
        		List<Post> userPosts = postService.findPostsByUserAndAudience(user, "public", "friend", post.getPageNumber());

        		for(int i=0; i<userPosts.size(); i++) {

        			PostModel thePost = new PostModel();
        			thePost.setOwn(false);
        			thePost.setEmail(user.getEmail());
        			thePost.setPostId(userPosts.get(i).getPostId());
        			thePost.setCreatorName(user.getFirstName()+" "+user.getLastName());
        			thePost.setCreationDate(userPosts.get(i).getDateAndTime());
        			thePost.setAudience(userPosts.get(i).getAudience());
        			thePost.setTextMessage(userPosts.get(i).getTextMessage());
        			thePost.setImageUrl(userPosts.get(i).getImageUrl());
        			thePost.setVideoUrl(userPosts.get(i).getVideoUrl());
        			thePost.setComments(commentsService.countCommentsByPost(userPosts.get(i)));
//        			thePost.setLikes(likesService.countLikesByPost(userPosts.get(i)));
//        			thePost.setDisLikes(0L);
//        			thePost.setLiked(likesService.isLiked(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
//        			thePost.setDisLiked(false);
        			thePost.setLikes(likesService.countLikesOrDisLikesByPost(userPosts.get(i), true));
        			thePost.setDisLikes(likesService.countLikesOrDisLikesByPost(userPosts.get(i), false));
        			thePost.setLiked(likesService.userLiked(user, userPosts.get(i)));
        			thePost.setDisLiked(likesService.userDisLiked(user, userPosts.get(i)));
        			thePost.setCommented(commentsService.isCommented(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
        			thePost.setCreatorImageUrl(userPosts.get(i).getUserId().getPhotoUrl());
        			thePost.setCreatorEmail(userPosts.get(i).getUserId().getEmail());
        			posts.add(thePost);
        		}
        		return posts;
    		}
			//post with audience of only public can be display
    		List<Post> userPosts = postService.findPostsByUserAndAudience(user, "public", post.getPageNumber());
    		
    		for(int i=0; i<userPosts.size(); i++) {

    			PostModel thePost = new PostModel();
    			thePost.setOwn(false);
    			thePost.setEmail(user.getEmail());
    			thePost.setPostId(userPosts.get(i).getPostId());
    			thePost.setCreatorName(user.getFirstName()+" "+user.getLastName());
    			thePost.setCreationDate(userPosts.get(i).getDateAndTime());
    			thePost.setAudience(userPosts.get(i).getAudience());
    			thePost.setTextMessage(userPosts.get(i).getTextMessage());
    			thePost.setImageUrl(userPosts.get(i).getImageUrl());
    			thePost.setVideoUrl(userPosts.get(i).getVideoUrl());
    			thePost.setComments(commentsService.countCommentsByPost(userPosts.get(i)));
//    			thePost.setLikes(likesService.countLikesByPost(userPosts.get(i)));
//    			thePost.setDisLikes(0L);
//    			thePost.setLiked(likesService.isLiked(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
//    			thePost.setDisLiked(false);
    			thePost.setLikes(likesService.countLikesOrDisLikesByPost(userPosts.get(i), true));
    			thePost.setDisLikes(likesService.countLikesOrDisLikesByPost(userPosts.get(i), false));
    			thePost.setLiked(likesService.userLiked(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
    			thePost.setDisLiked(likesService.userDisLiked(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
    			thePost.setCommented(commentsService.isCommented(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
    			thePost.setCreatorImageUrl(userPosts.get(i).getUserId().getPhotoUrl());
    			thePost.setCreatorEmail(userPosts.get(i).getUserId().getEmail());
    			posts.add(thePost);
    		}
    		return posts;
    	}
    	
    	//send posts for timeLine
    	//send public posts
    	//(not for now) send users friends posts by most recent
		List<Post> userPosts = postService.findAllPostsByPagination(post.getPageNumber());

		for(int i=0; i<userPosts.size(); i++) {

			PostModel thePost = new PostModel();
			if(userPosts.get(i).getUserId().equals(userService.getUsersByEmail(principal.getName()))) {
				thePost.setOwn(true);
			}else {
				thePost.setOwn(false);
			}
			thePost.setEmail(userPosts.get(i).getUserId().getEmail());
			thePost.setPostId(userPosts.get(i).getPostId());
			thePost.setCreatorName(userPosts.get(i).getUserId().getFirstName()+" "+userPosts.get(i).getUserId().getLastName());
			thePost.setCreationDate(userPosts.get(i).getDateAndTime());
			thePost.setAudience(userPosts.get(i).getAudience());
			thePost.setTextMessage(userPosts.get(i).getTextMessage());
			thePost.setImageUrl(userPosts.get(i).getImageUrl());
			thePost.setVideoUrl(userPosts.get(i).getVideoUrl());
			thePost.setComments(commentsService.countCommentsByPost(userPosts.get(i)));
//			thePost.setLikes(likesService.countLikesByPost(userPosts.get(i)));
//			thePost.setDisLikes(0L);
//			thePost.setLiked(likesService.isLiked(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
//			thePost.setDisLiked(false);
			thePost.setLikes(likesService.countLikesOrDisLikesByPost(userPosts.get(i), true));
			thePost.setDisLikes(likesService.countLikesOrDisLikesByPost(userPosts.get(i), false));
			thePost.setLiked(likesService.userLiked(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
			thePost.setDisLiked(likesService.userDisLiked(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
			thePost.setCommented(commentsService.isCommented(userService.getUsersByEmail(principal.getName()), userPosts.get(i)));
			thePost.setCreatorImageUrl(userPosts.get(i).getUserId().getPhotoUrl());
			thePost.setCreatorEmail(userPosts.get(i).getUserId().getEmail());
			posts.add(thePost);
		}
		return posts;
    }

//    like or dislike or detach a post on my account, timeLine, others account
	@PostMapping("like")
    public String likeOrDislikePost(
			@RequestParam(value = "like", required = true) boolean like, 
			@RequestParam(value = "null", required = true) boolean detach, 
			@RequestParam(value = "postId", required = true) String postId, 
			HttpServletResponse response,
			Principal principal
    		) throws IOException {
    	
    	Optional<Post> post = postService.getPostById(Integer.parseInt(postId));
    	if(post.isPresent()) {
    		User postOwner = post.get().getUserId();
    		User loggedInUser = userService.getUsersByEmail(principal.getName());
    		String audience = post.get().getAudience();
    		
    		if(audience.equalsIgnoreCase("PUBLIC")) {
    			if(detach==true) {
    				likesService.deleteByUserIdAndPostId(loggedInUser, post.get());
    				return "detach successfully";
    			}
    			//if row exist or not
    			if(likesService.isLiked(loggedInUser, post.get())) {
    				Likes likeRow = likesService.findByUserIdAndPostId(loggedInUser, post.get());
    				likeRow.setDateAndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
    				likeRow.setLiked(like);
    				likesService.updateLikes(likeRow);
    				return like+"ed succefully";
    			}else {
    				Likes likeRow = new Likes();
    				likeRow.setDateAndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
    				likeRow.setPostId(post.get());
    				likeRow.setUserId(loggedInUser);
    				likeRow.setLiked(like);
    				likesService.saveLikes(likeRow);
    				return like+"ed succefully";
    			}
    		}else{
    			if(friendService.isUserFriend(postOwner, loggedInUser) || postOwner.equals(loggedInUser)){
        			if(detach==true) {
        				likesService.deleteByUserIdAndPostId(loggedInUser, post.get());
        				return "detach successfully";
        			}
        			if(likesService.isLiked(loggedInUser, post.get())) {
        				Likes likeRow = likesService.findByUserIdAndPostId(loggedInUser, post.get());
        				likeRow.setDateAndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
        				likeRow.setLiked(like);
        				likesService.updateLikes(likeRow);
        				return like+"ed succefully";
        			}else {
        				Likes likeRow = new Likes();
        				likeRow.setDateAndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
        				likeRow.setPostId(post.get());
        				likeRow.setUserId(loggedInUser);
        				likeRow.setLiked(like);
        				likesService.saveLikes(likeRow);
        				return like+"ed succefully";
        			}
    			}else {
    		        response.sendError(400, "not allowed to like or dislike");
    		    	return "not allowed to like or dislike";
    			}
    		}
    	}
        response.sendError(400, "post not found");
    	return "post not found";
    }
	
	//showing comments based on post
	@PostMapping("comments")
	public List<CommentModel> showComments(
			@RequestParam(value = "postId", required = true) Integer postId, 
			@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			HttpServletResponse response,
			Principal principal
			) throws IOException{
		//is post exist
		Optional<Post> post = postService.getPostById(postId);
		if(post.isPresent()) {
			//is post accessible 
			if(post.get().getAudience().equalsIgnoreCase("PUBLIC") || 
			   post.get().getUserId().equals(userService.getUsersByEmail(principal.getName())) ||
			   (friendService.isUserFriend(post.get().getUserId(), userService.getUsersByEmail(principal.getName())) &&
				!post.get().getAudience().equalsIgnoreCase("PRIVATE"))) {
				List<Comments> comments = commentsService.findCommentsByPostId(post.get(), pageNumber);
				List<CommentModel> commentModels = new ArrayList<>();
				for(int i=0; i<comments.size(); i++) {
					CommentModel commentModel = new CommentModel();
					
					if(comments.get(i).getUserId().equals(userService.getUsersByEmail(principal.getName()))) {
						commentModel.setOwn(true);
					}else {
						commentModel.setOwn(false);
					}
					commentModel.setCommentId(comments.get(i).getCommentId());
					commentModel.setPostId(comments.get(i).getPostId().getPostId());
					User user = comments.get(i).getUserId();
					commentModel.setCreatorName(user.getFirstName()+" "+user.getLastName());
					commentModel.setCreatorEmail(user.getEmail());
					commentModel.setCreatorImageUrl(user.getPhotoUrl());
					commentModel.setCreationDate(comments.get(i).getDateAndTime());
					commentModel.setContent(comments.get(i).getTextMessage());
					commentModel.setPageNumber(pageNumber);
					commentModels.add(commentModel);
				}
				return commentModels;
			}else {
		        response.sendError(400, "user is not allowed to see post comments");
			}
		}
        response.sendError(400, "post not found");
		return null;
	}

//    comment a post on my account, timeLine, others account
	@PostMapping("submitComment")
    public CommentModel createComment(
    		@Valid  @RequestBody CommentModel commentModel, 
			HttpServletResponse response,
    		Principal principal
    		) throws IOException {
		//is post exist
		System.out.println(commentModel.getPostId());
		System.out.println(commentModel.getContent());
		
		Optional<Post> post = postService.getPostById(commentModel.getPostId());
		if(post.isPresent()) {
			//is post accessible 
			if(post.get().getAudience().equalsIgnoreCase("PUBLIC") || 
			   post.get().getUserId().equals(userService.getUsersByEmail(principal.getName())) ||
			   (friendService.isUserFriend(post.get().getUserId(), userService.getUsersByEmail(principal.getName())) &&
				!post.get().getAudience().equalsIgnoreCase("PRIVATE"))) {
				Comments comment = new Comments();
				comment.setUserId(userService.getUsersByEmail(principal.getName()));
				comment.setPostId(postService.getPostById(commentModel.getPostId()).get());
				comment.setDateAndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
				comment.setTextMessage(commentModel.getContent());
				commentsService.saveComment(comment);
//				return "success";
				CommentModel userComment = new CommentModel();
				userComment.setOwn(true);
				userComment.setCommentId(comment.getCommentId());
				userComment.setPostId(commentModel.getPostId());
				User user = userService.getUsersByEmail(principal.getName());
				userComment.setCreatorName(user.getFirstName()+" "+user.getLastName());
				userComment.setCreatorEmail(user.getEmail());
				userComment.setCreatorImageUrl(user.getPhotoUrl());
				userComment.setCreationDate(comment.getDateAndTime());
				userComment.setContent(comment.getTextMessage());
				userComment.setPageNumber(0);

				
				return userComment;
				
			}else {
		        response.sendError(400, "user is not allowed to comment on this post");
			}
		}
        response.sendError(400, "post not found");
		return null;

    	
    }
	
	
	@PostMapping("userProfile")
	public UserModel showUserProfile(
			@RequestParam(value = "email", required = true) String userEmail, 
			HttpServletResponse response,
			Principal principal
			) throws IOException
	{
		//if user of these email exist
		User user = userService.getUsersByEmail(userEmail);
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		if(user!=null) {
			UserModel userModel = new UserModel();
			if(loggedInUser.equals(user)) {
				userModel.setOwn(true);
			}else {
				userModel.setOwn(false);
			}
			if(friendService.isUserFriend(user, loggedInUser)) {
				userModel.setFriend(true);
			}else {
				userModel.setFriend(false);
			}
			if(friendRequestService.isFriendRequestSent(loggedInUser, user)) {
				userModel.setFriendRequestSentToThisUser(true);
			}else {
				userModel.setFriendRequestSentToThisUser(false);
			}
			if(friendRequestService.isFriendRequestSent(user, loggedInUser)) {
				userModel.setFriendRequestSentByThisUser(true);
			}else {
				userModel.setFriendRequestSentByThisUser(false);
			}
			userModel.setEmail(user.getEmail());
			userModel.setFirstName(user.getFirstName());
			userModel.setLastName(user.getLastName());
			userModel.setPhotoUrl(user.getPhotoUrl());
			userModel.setAddress(user.getAddress());
			userModel.setPhone(user.getPhone());
			userModel.setGender(user.getGender());
			userModel.setDob(user.getDob());
			userModel.setJobInfo(user.getJobInfo());
			userModel.setAcademicInfo(user.getAcademicInfo());
			userModel.setPersonalInfo(user.getPersonalInfo());
			userModel.setLastAppearance(user.getLastAppearance());
			return userModel;
		}else {
			response.sendError(400, "user of this email not found");
		}
		return null;
	}

    //(remaining) delete the image if not sent
	@PostMapping(value = "updateUserProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public UserModel updateUserProfile(
			@Valid @ModelAttribute UserModel userModel, 
			@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletResponse response,
			Principal principal
			) throws IOException, InterruptedException {
		
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		if(userModel.getEmail().equals(principal.getName())) {
			loggedInUser.setEmail(userModel.getEmail());
			loggedInUser.setPassword(userModel.getPassword());
			loggedInUser.setFirstName(userModel.getFirstName());
			loggedInUser.setLastName(userModel.getLastName());
			
			if(file!=null) {
				if(file.getContentType().startsWith("image")) {
					String fileName = resourceService.createResource(file, "jpg");
					loggedInUser.setPhotoUrl(fileName);
				}
			}
			loggedInUser.setAddress(userModel.getAddress());
			loggedInUser.setPhone(userModel.getPhone());
			loggedInUser.setGender(userModel.getGender());
			loggedInUser.setDob(userModel.getDob());
			loggedInUser.setJobInfo(userModel.getJobInfo());
			loggedInUser.setAcademicInfo(userModel.getAcademicInfo());
			loggedInUser.setPersonalInfo(userModel.getPersonalInfo());
			userService.updateUser(loggedInUser);
		}else {
			User existanceOfUserByEmail = userService.getUsersByEmail(userModel.getEmail());
			if(existanceOfUserByEmail==null) {
				loggedInUser.setEmail(userModel.getEmail());
				loggedInUser.setPassword(userModel.getPassword());
				loggedInUser.setFirstName(userModel.getFirstName());
				loggedInUser.setLastName(userModel.getLastName());
				
				if(file!=null) {
					if(file.getContentType().startsWith("image")) {
						String fileName = resourceService.createResource(file, "jpg");
						loggedInUser.setPhotoUrl(fileName);
					}
				}
				loggedInUser.setAddress(userModel.getAddress());
				loggedInUser.setPhone(userModel.getPhone());
				loggedInUser.setGender(userModel.getGender());
				loggedInUser.setDob(userModel.getDob());
				loggedInUser.setJobInfo(userModel.getJobInfo());
				loggedInUser.setAcademicInfo(userModel.getAcademicInfo());
				loggedInUser.setPersonalInfo(userModel.getPersonalInfo());
				userService.updateUser(loggedInUser);
			}else {
				response.sendError(400, "email is already taken");
				return null;
			}
		}

		UserModel newUserModel = new UserModel();

		newUserModel.setOwn(true);
		newUserModel.setFriend(false);
		newUserModel.setFriendRequestSentToThisUser(false);
		newUserModel.setFriendRequestSentByThisUser(false);
		newUserModel.setEmail(loggedInUser.getEmail());
		newUserModel.setFirstName(loggedInUser.getFirstName());
		newUserModel.setLastName(loggedInUser.getLastName());
		newUserModel.setPhotoUrl(loggedInUser.getPhotoUrl());
		newUserModel.setAddress(loggedInUser.getAddress());
		newUserModel.setPhone(loggedInUser.getPhone());
		newUserModel.setGender(loggedInUser.getGender());
		newUserModel.setDob(loggedInUser.getDob());
		newUserModel.setJobInfo(loggedInUser.getJobInfo());
		newUserModel.setAcademicInfo(loggedInUser.getAcademicInfo());
		newUserModel.setPersonalInfo(loggedInUser.getPersonalInfo());
//			newUserModel.setLastAppearance(loggedInUser.getLastAppearance());
			return newUserModel;
	}

	
////////////////////////////////start////////////////////////////////////////////////
	@PostMapping("friends")
	public List<UserModel> friends(
			@RequestParam(value = "pageNumber", required = true) int page,
			HttpServletResponse response, 
			Principal principal) 
	{
		List<User> friends = userService.getFriends(userService.getUsersByEmail(principal.getName()), page);
		List<UserModel> friendModels = new ArrayList<>();
		for(int i=0; i<friends.size(); i++) {
			UserModel newUserModel = new UserModel();
			
			newUserModel.setOwn(false);
			newUserModel.setFriend(true);
			newUserModel.setFriendRequestSentToThisUser(false);
			newUserModel.setFriendRequestSentByThisUser(false);
			newUserModel.setEmail(friends.get(i).getEmail());
			newUserModel.setFirstName(friends.get(i).getFirstName());
			newUserModel.setLastName(friends.get(i).getLastName());
			newUserModel.setPhotoUrl(friends.get(i).getPhotoUrl());
			newUserModel.setAddress(friends.get(i).getAddress());
			newUserModel.setPhone(friends.get(i).getPhone());
			newUserModel.setGender(friends.get(i).getGender());
			newUserModel.setDob(friends.get(i).getDob());
			newUserModel.setJobInfo(friends.get(i).getJobInfo());
			newUserModel.setAcademicInfo(friends.get(i).getAcademicInfo());
			newUserModel.setPersonalInfo(friends.get(i).getPersonalInfo());

			friendModels.add(newUserModel);
		}
		return friendModels;
		
	}

	@PostMapping("allFriends")
	public List<UserModel> allFriends(
			HttpServletResponse response, 
			Principal principal) 
	{
		List<User> friends = userService.getAllFriends(userService.getUsersByEmail(principal.getName()));
		List<UserModel> friendModels = new ArrayList<>();
		for(int i=0; i<friends.size(); i++) {
			UserModel newUserModel = new UserModel();
			
			newUserModel.setOwn(false);
			newUserModel.setFriend(true);
			newUserModel.setFriendRequestSentToThisUser(false);
			newUserModel.setFriendRequestSentByThisUser(false);
			newUserModel.setEmail(friends.get(i).getEmail());
			newUserModel.setFirstName(friends.get(i).getFirstName());
			newUserModel.setLastName(friends.get(i).getLastName());
			newUserModel.setPhotoUrl(friends.get(i).getPhotoUrl());
			newUserModel.setAddress(friends.get(i).getAddress());
			newUserModel.setPhone(friends.get(i).getPhone());
			newUserModel.setGender(friends.get(i).getGender());
			newUserModel.setDob(friends.get(i).getDob());
			newUserModel.setJobInfo(friends.get(i).getJobInfo());
			newUserModel.setAcademicInfo(friends.get(i).getAcademicInfo());
			newUserModel.setPersonalInfo(friends.get(i).getPersonalInfo());

			friendModels.add(newUserModel);
		}
		return friendModels;
		
	}

	@PostMapping("friendRequestsSent")
	public List<UserModel> friendRequestsSent(
			@RequestParam(value = "pageNumber", required = true) int page, 
			HttpServletResponse response,
			Principal principal) {
		
		List<User> friendRequestSentUsers = userService.getFriendRequestSentUsers(userService.getUsersByEmail(principal.getName()), page);
		List<UserModel> friendRequestSentUsersModels = new ArrayList<>();

		for(int i=0; i<friendRequestSentUsers.size(); i++) {
			UserModel newUserModel = new UserModel();
			
			newUserModel.setOwn(false);
			newUserModel.setFriend(false);
			newUserModel.setFriendRequestSentToThisUser(true);
			newUserModel.setFriendRequestSentByThisUser(false);
			newUserModel.setEmail(friendRequestSentUsers.get(i).getEmail());
			newUserModel.setFirstName(friendRequestSentUsers.get(i).getFirstName());
			newUserModel.setLastName(friendRequestSentUsers.get(i).getLastName());
			newUserModel.setPhotoUrl(friendRequestSentUsers.get(i).getPhotoUrl());
			newUserModel.setAddress(friendRequestSentUsers.get(i).getAddress());
			newUserModel.setPhone(friendRequestSentUsers.get(i).getPhone());
			newUserModel.setGender(friendRequestSentUsers.get(i).getGender());
			newUserModel.setDob(friendRequestSentUsers.get(i).getDob());
			newUserModel.setJobInfo(friendRequestSentUsers.get(i).getJobInfo());
			newUserModel.setAcademicInfo(friendRequestSentUsers.get(i).getAcademicInfo());
			newUserModel.setPersonalInfo(friendRequestSentUsers.get(i).getPersonalInfo());

			friendRequestSentUsersModels.add(newUserModel);

		}
		return friendRequestSentUsersModels;

	}
	@PostMapping("friendRequestsReceived")
	public List<UserModel> friendRequestsReceived(
			@RequestParam(value = "pageNumber", required = true) int page, 
			HttpServletResponse response,
			Principal principal) {
		List<User> friendRequestReceivedUsers = userService.getFriendRequestReceivedUsers(userService.getUsersByEmail(principal.getName()), page);
		List<UserModel> friendRequestReceivedUsersModels = new ArrayList<>();
		
		for(int i=0; i<friendRequestReceivedUsers.size(); i++) {
			UserModel newUserModel = new UserModel();
			
			newUserModel.setOwn(false);
			newUserModel.setFriend(false);
			newUserModel.setFriendRequestSentToThisUser(false);
			newUserModel.setFriendRequestSentByThisUser(true);
			newUserModel.setEmail(friendRequestReceivedUsers.get(i).getEmail());
			newUserModel.setFirstName(friendRequestReceivedUsers.get(i).getFirstName());
			newUserModel.setLastName(friendRequestReceivedUsers.get(i).getLastName());
			newUserModel.setPhotoUrl(friendRequestReceivedUsers.get(i).getPhotoUrl());
			newUserModel.setAddress(friendRequestReceivedUsers.get(i).getAddress());
			newUserModel.setPhone(friendRequestReceivedUsers.get(i).getPhone());
			newUserModel.setGender(friendRequestReceivedUsers.get(i).getGender());
			newUserModel.setDob(friendRequestReceivedUsers.get(i).getDob());
			newUserModel.setJobInfo(friendRequestReceivedUsers.get(i).getJobInfo());
			newUserModel.setAcademicInfo(friendRequestReceivedUsers.get(i).getAcademicInfo());
			newUserModel.setPersonalInfo(friendRequestReceivedUsers.get(i).getPersonalInfo());

			friendRequestReceivedUsersModels.add(newUserModel);

		}
		return friendRequestReceivedUsersModels;

	}
	@PostMapping("sendFriendRequest")
	public UserModel sendFriendRequest(
			@RequestParam(value = "email", required = true) String userEmail, 
			HttpServletResponse response,
			Principal principal) throws IOException {
		//user with this email should exist first
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		User friendRequestSendingUser = userService.getUsersByEmail(userEmail);
		if(friendRequestSendingUser==null) {
			response.sendError(404, "user for send friend reequest not found");
			return null;
		}
		//user should not be a friend
		if(friendService.isUserFriend(loggedInUser, friendRequestSendingUser)) {
			response.sendError(404, "user is already friend");
			return null;
		}

		//no friend request sent to user already
		if(friendRequestService.isFriendRequestSent(loggedInUser, friendRequestSendingUser)) {
			response.sendError(404, "already sent friend request");
			return null;
		}
		
		//no friend request received by user already
		if(friendRequestService.isFriendRequestSent(friendRequestSendingUser, loggedInUser)) {
			response.sendError(404, "already received a friend request from this user");
			return null;
		}
		
		FriendRequest friendRequest = new FriendRequest();
		friendRequest.setFriend1RequestId(loggedInUser);
		friendRequest.setFriend2RequestId(friendRequestSendingUser);
		friendRequest.setDateAndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		friendRequest.setReaded(false);
		friendRequestService.saveFriendRequest(friendRequest);

		UserModel userModel = new UserModel();
		userModel.setOwn(false);
		userModel.setFriend(false);
		userModel.setFriendRequestSentToThisUser(true);
		userModel.setFriendRequestSentByThisUser(false);
		userModel.setEmail(friendRequestSendingUser.getEmail());
		userModel.setFirstName(friendRequestSendingUser.getFirstName());
		userModel.setLastName(friendRequestSendingUser.getLastName());
		userModel.setPhotoUrl(friendRequestSendingUser.getPhotoUrl());
		userModel.setAddress(friendRequestSendingUser.getAddress());
		userModel.setPhone(friendRequestSendingUser.getPhone());
		userModel.setGender(friendRequestSendingUser.getGender());
		userModel.setDob(friendRequestSendingUser.getDob());
		userModel.setJobInfo(friendRequestSendingUser.getJobInfo());
		userModel.setAcademicInfo(friendRequestSendingUser.getAcademicInfo());
		userModel.setPersonalInfo(friendRequestSendingUser.getPersonalInfo());

		return userModel;

	}
	@PostMapping("cancelSentFriendRequest")
	public UserModel cancelSentFriendRequest(
			@RequestParam(value = "email", required = true) String userEmail, 
			HttpServletResponse response,
			Principal principal) throws IOException {
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		User friendRequestCancellingUser = userService.getUsersByEmail(userEmail);
		if(friendRequestCancellingUser==null) {
			response.sendError(404, "user for cancel friend reequest not found");
			return null;
		}
		
		//logged in user should already send friend request to this user
		if(friendRequestService.isFriendRequestSent(loggedInUser, friendRequestCancellingUser)) {
			friendRequestService.deleteFriendRequest(loggedInUser, friendRequestCancellingUser);
			UserModel userModel = new UserModel();
			userModel.setOwn(false);
			userModel.setFriend(false);
			userModel.setFriendRequestSentToThisUser(false);
			userModel.setFriendRequestSentByThisUser(false);
			userModel.setEmail(friendRequestCancellingUser.getEmail());
			userModel.setFirstName(friendRequestCancellingUser.getFirstName());
			userModel.setLastName(friendRequestCancellingUser.getLastName());
			userModel.setPhotoUrl(friendRequestCancellingUser.getPhotoUrl());
			userModel.setAddress(friendRequestCancellingUser.getAddress());
			userModel.setPhone(friendRequestCancellingUser.getPhone());
			userModel.setGender(friendRequestCancellingUser.getGender());
			userModel.setDob(friendRequestCancellingUser.getDob());
			userModel.setJobInfo(friendRequestCancellingUser.getJobInfo());
			userModel.setAcademicInfo(friendRequestCancellingUser.getAcademicInfo());
			userModel.setPersonalInfo(friendRequestCancellingUser.getPersonalInfo());

			return userModel;

		}
			response.sendError(404, "there is no friend reuqest to cancel");
			return null;

	}
	@PostMapping("acceptFriendRequest")
	public UserModel acceptFriendRequest(
			@RequestParam(value = "email", required = true) String userEmail, 
			HttpServletResponse response,
			Principal principal) throws IOException {

		User loggedInUser = userService.getUsersByEmail(principal.getName());
		User friendRequestAcceptingUser = userService.getUsersByEmail(userEmail);
		//if user exist
		if(friendRequestAcceptingUser==null) {
			response.sendError(404, "user not found");
			return null;
		}

		//if already received friend request
		if(friendRequestService.isFriendRequestSent(friendRequestAcceptingUser, loggedInUser)) {
			friendRequestService.deleteFriendRequest(friendRequestAcceptingUser, loggedInUser);

			Friend friend = new Friend();
			friend.setFriend2Id(friendRequestAcceptingUser);
			friend.setFriend1Id(loggedInUser);
			friend.setDateAndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
			friendService.saveFriend(friend);

			UserModel userModel = new UserModel();
			userModel.setOwn(false);
			userModel.setFriend(true);
			userModel.setFriendRequestSentToThisUser(false);
			userModel.setFriendRequestSentByThisUser(false);
			userModel.setEmail(friendRequestAcceptingUser.getEmail());
			userModel.setFirstName(friendRequestAcceptingUser.getFirstName());
			userModel.setLastName(friendRequestAcceptingUser.getLastName());
			userModel.setPhotoUrl(friendRequestAcceptingUser.getPhotoUrl());
			userModel.setAddress(friendRequestAcceptingUser.getAddress());
			userModel.setPhone(friendRequestAcceptingUser.getPhone());
			userModel.setGender(friendRequestAcceptingUser.getGender());
			userModel.setDob(friendRequestAcceptingUser.getDob());
			userModel.setJobInfo(friendRequestAcceptingUser.getJobInfo());
			userModel.setAcademicInfo(friendRequestAcceptingUser.getAcademicInfo());
			userModel.setPersonalInfo(friendRequestAcceptingUser.getPersonalInfo());

			return userModel;

		}
		
		response.sendError(404, "there is no friend reuqest received to accept");
		return null;

	}
	
	
	
	@PostMapping("unFriend")
	public UserModel unFriend(
			@RequestParam(value = "email", required = true) String userEmail, 
			HttpServletResponse response,
			Principal principal) throws IOException {
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		User unFriendingUser = userService.getUsersByEmail(userEmail);
		//if user exist
		if(unFriendingUser==null) {
			response.sendError(404, "user not found");
			return null;
		}
		
		if(friendService.isUserFriend(loggedInUser, unFriendingUser)) {
			friendService.unFriend(loggedInUser, unFriendingUser);
			UserModel userModel = new UserModel();
			userModel.setOwn(false);
			userModel.setFriend(false);
			userModel.setFriendRequestSentToThisUser(false);
			userModel.setFriendRequestSentByThisUser(false);
			userModel.setEmail(unFriendingUser.getEmail());
			userModel.setFirstName(unFriendingUser.getFirstName());
			userModel.setLastName(unFriendingUser.getLastName());
			userModel.setPhotoUrl(unFriendingUser.getPhotoUrl());
			userModel.setAddress(unFriendingUser.getAddress());
			userModel.setPhone(unFriendingUser.getPhone());
			userModel.setGender(unFriendingUser.getGender());
			userModel.setDob(unFriendingUser.getDob());
			userModel.setJobInfo(unFriendingUser.getJobInfo());
			userModel.setAcademicInfo(unFriendingUser.getAcademicInfo());
			userModel.setPersonalInfo(unFriendingUser.getPersonalInfo());

			return userModel;

		}
		
		response.sendError(404, "user is not your friend already");
		return null;

	}

	@PostMapping("people")
	public List<UserModel> people(
			@RequestParam(value = "pageNumber", required = true) int page, 
			@RequestParam(value = "keyword", required = true) String keyword, 
			HttpServletResponse response,
			Principal principal) {
		
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		List<User> interestedUsers = userService.findUsernameContaining("%"+keyword+"%", page);
		List<UserModel> interestedUserModels = new ArrayList<>();
		
		for(int i=0; i<interestedUsers.size(); i++) {
			UserModel userModel = new UserModel();
			
			if(interestedUsers.get(i).equals(loggedInUser)) {
				userModel.setOwn(true);
			}else {userModel.setOwn(false);}
			
			if(friendService.isUserFriend(loggedInUser, interestedUsers.get(i))) {
				userModel.setFriend(true);
			}else {userModel.setFriend(false);}
			
			if(friendRequestService.isFriendRequestSent(loggedInUser, interestedUsers.get(i))) {
				userModel.setFriendRequestSentToThisUser(true);
			}else {userModel.setFriendRequestSentToThisUser(false);}

			if(friendRequestService.isFriendRequestSent(interestedUsers.get(i), loggedInUser)) {
				userModel.setFriendRequestSentByThisUser(true);
			}else {userModel.setFriendRequestSentByThisUser(false);}

			userModel.setEmail(interestedUsers.get(i).getEmail());
			userModel.setFirstName(interestedUsers.get(i).getFirstName());
			userModel.setLastName(interestedUsers.get(i).getLastName());
			userModel.setPhotoUrl(interestedUsers.get(i).getPhotoUrl());
			userModel.setAddress(interestedUsers.get(i).getAddress());
			userModel.setPhone(interestedUsers.get(i).getPhone());
			userModel.setGender(interestedUsers.get(i).getGender());
			userModel.setDob(interestedUsers.get(i).getDob());
			userModel.setJobInfo(interestedUsers.get(i).getJobInfo());
			userModel.setAcademicInfo(interestedUsers.get(i).getAcademicInfo());
			userModel.setPersonalInfo(interestedUsers.get(i).getPersonalInfo());

			interestedUserModels.add(userModel);
		}
		
		return interestedUserModels;
	}

////////////////////////////////finish////////////////////////////////////////////////

	
	
	//delete user account
	
	@PostMapping("deleteUserAccount")
	public String deleteUserAccount(HttpServletResponse response, Principal principal) throws IOException {
		User deletingUser = userService.getUsersByEmail(principal.getName());
		if(deletingUser.getPhotoUrl()!=null || deletingUser.getPhotoUrl()!="") {
			resourceService.deleteResource(deletingUser.getPhotoUrl(), "jpg");
		}
		userService.deleteUser(userService.getUsersByEmail(principal.getName()));
		return "account is deleted";
	}	

    @GetMapping("image/{fileName}")
    public void serveImage(@PathVariable String fileName, HttpServletResponse response) throws IOException {
    	response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		InputStream data = resourceService.getResource(fileName, "jpg");
		if(data==null) {
			response.sendError(400, "image not found");
		}else {
			StreamUtils.copy(data, response.getOutputStream());
		}
    }
    
    
	@PostMapping("/communicateUser1")
	public String communicateUser1(/* @Valid @RequestBody ChatModel chat */) {//@Valid @RequestBody ChatModel chat
//		for (int i=0; i<chat.getTo().size(); i++){
//			System.out.println("/topic/"+chat.getTo().get(i));
//			this.simpMessagingTemplate.convertAndSend("/topic/"+chat.getTo().get(i), chat);
//		}
		
		List<Content> contents = new ArrayList<>();
		Content content = new Content();
		content.setContent("this is text content");
		content.setContentType("TEXT");
		Content content1 = new Content();
		content1.setContent("this is image content");
		content1.setContentType("image");
		Content content2 = new Content();
		content2.setContent("this is video content");
		content2.setContentType("video");
		contents.add(content);
		contents.add(content1);
		contents.add(content2);
		
		ChatModel chat = new ChatModel();
		chat.setFirstName("ratnadeep");
		chat.setLastName("naik");
		chat.setFrom("ratnadeep@gmail.com");
//		chat.setPhtoUrl("myphoto");
		chat.setTimeStamp(new java.sql.Timestamp(new java.util.Date().getTime()));
		//chat.setContent(contents);
		
		this.simpMessagingTemplate.convertAndSend("/topic/user1@gmail.com", chat);

		return "success";
		}

		@PostMapping(value = "communicate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ChatModel communicate(
				@Valid @ModelAttribute ChatModel chat,
				@RequestParam(value = "file", required = false) MultipartFile file, 
				@RequestParam(value = "fileFirst", defaultValue = "false", required = false) boolean fileFirst,
				HttpServletResponse response,
				Principal principal) throws IOException, InterruptedException {

			User sender = userService.getUsersByEmail(principal.getName());
			User receiver = userService.getUsersByEmail(chat.getTo());

			//if message is empty
			
			if(file==null && (chat.getContent()==null || chat.getContent().isEmpty() || chat.getContent().get(0)==null || chat.getContent().get(0)=="")) {
				response.sendError(404,"message is empty");
				return null;
			}

			//if receiver not exist or receiver is same as sender
			if(receiver==null || receiver.equals(sender)) {
				response.sendError(404,"user not found");
				return null;
			}else {
				//if receiver not friend
				if(!friendService.isUserFriend(sender, receiver)) {
					response.sendError(404,"user is not your friend");
					return null;
				}
			}
			
			FriendChat friendChat = new FriendChat();
			friendChat.setFriend1ChatId(sender);
			friendChat.setFriend2ChatId(receiver);
			friendChat.setDateAndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
			friendChat.setReaded(false);

			if(file!=null) {
				if(fileFirst==true) {
					if(file.getContentType().startsWith("video")) {
						String fileName = resourceService.createResource(file, "mp4");
						friendChat.setContentWithContentType1("VIDEO "+fileName);
					}else if(file.getContentType().startsWith("image")) {
						String fileName = resourceService.createResource(file, "jpg");
						friendChat.setContentWithContentType1("IMAGE "+fileName);
					}
					if(chat.getContent()!=null && !chat.getContent().isEmpty() && chat.getContent().get(0)!=null && chat.getContent().get(0)!="") {
						friendChat.setContentWithContentType2("TEXT "+chat.getContent().get(0));
					}

				}else if(chat.getContent()==null || chat.getContent().isEmpty() || chat.getContent().get(0)==null || chat.getContent().get(0)=="") {
					if(file.getContentType().startsWith("video")) {
						String fileName = resourceService.createResource(file, "mp4");
						friendChat.setContentWithContentType1("VIDEO "+fileName);
					}else if(file.getContentType().startsWith("image")) {
						String fileName = resourceService.createResource(file, "jpg");
						friendChat.setContentWithContentType1("IMAGE  "+fileName);
					}
				}else {
					
					friendChat.setContentWithContentType1("TEXT "+chat.getContent().get(0));
					if(file.getContentType().startsWith("video")) {
						String fileName = resourceService.createResource(file, "mp4");
						friendChat.setContentWithContentType2("VIDEO "+fileName);
					}else if(file.getContentType().startsWith("image")) {
						String fileName = resourceService.createResource(file, "jpg");
						friendChat.setContentWithContentType2("IMAGE  "+fileName);
					}
				}
			}else {
				friendChat.setContentWithContentType1("TEXT "+chat.getContent().get(0));
				
			}

			friendChat.setStatus(1);
			friendChatService.saveFriendChat(friendChat);
			ChatModel chatModel = new ChatModel();
			chatModel.setChatId(friendChat.getFriendChatId());
			chatModel.setFrom(principal.getName());
			chatModel.setTo(chat.getTo());
			chatModel.setTimeStamp(friendChat.getDateAndTime());
			chatModel.setFirstName(sender.getFirstName());
			chatModel.setLastName(sender.getLastName());
			chatModel.setPhotoUrl(sender.getPhotoUrl());
			chatModel.setOwn(false);
			List<String> contentTypes= new ArrayList<>();
			List<String> contents = new ArrayList<>();
			List<String> contentWithType1 = giveContentTypeAndContent(friendChat.getContentWithContentType1());
			contentTypes.add(contentWithType1.get(0));
			contents.add(contentWithType1.get(1));
			if(friendChat.getContentWithContentType2()!=null && !friendChat.getContentWithContentType2().isEmpty()) {
				List<String> contentWithType2 = giveContentTypeAndContent(friendChat.getContentWithContentType2());
				contentTypes.add(contentWithType2.get(0));
				contents.add(contentWithType2.get(1));
			}

			chatModel.setContent(contents);
			chatModel.setContentType(contentTypes);
			chatModel.setStatus(1);
			this.simpMessagingTemplate.convertAndSend("/topic/"+chat.getTo(), chatModel);
			chatModel.setOwn(true);
			return chatModel;

		}
		

	
	@PostMapping(value = "previousChat")
	public List<ChatModel> previousChat(
			@RequestParam(value = "friendEamil", required = true) String friendEamil, 
			@RequestParam(value = "earliestChatId", required = false) int earliestChatId,
			HttpServletResponse response,
			Principal principal) throws IOException {
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		User friendUser = userService.getUsersByEmail(friendEamil);
		//if user not exist
		if(friendUser==null) {
			response.sendError(404, "user not found");
			return null;
		}
		//if user is not friend
		if(!friendService.isUserFriend(loggedInUser, friendUser)) {
			response.sendError(404, "user is not your friend");
			return null;
		}

		List<FriendChat> friendChats = friendChatService.userChatsByFriendLastMessageIdAndQuantity(loggedInUser, friendUser, earliestChatId, 15);
		List<ChatModel> chats = new ArrayList<>();
		for(int i=0; i<friendChats.size();i++) {
			User receiver = friendChats.get(i).getFriend2ChatId();
			User sender = friendChats.get(i).getFriend1ChatId();
			
			ChatModel chatModel = new ChatModel();
			chatModel.setChatId(friendChats.get(i).getFriendChatId());
			chatModel.setFrom(sender.getEmail());
			chatModel.setTo(receiver.getEmail());
			chatModel.setTimeStamp(friendChats.get(i).getDateAndTime());
			chatModel.setFirstName(sender.getFirstName());
			chatModel.setLastName(sender.getLastName());
			chatModel.setPhotoUrl(sender.getPhotoUrl());
			if(sender.equals(loggedInUser)) {
				chatModel.setOwn(true);
			}else {
				chatModel.setOwn(false);
			}

			List<String> contentTypes= new ArrayList<>();
			List<String> contents = new ArrayList<>();
			List<String> contentWithType1 = giveContentTypeAndContent(friendChats.get(i).getContentWithContentType1());
			contentTypes.add(contentWithType1.get(0));
			contents.add(contentWithType1.get(1));

			if(friendChats.get(i).getContentWithContentType2()!=null && !friendChats.get(i).getContentWithContentType2().isEmpty()) {
				List<String> contentWithType2 = giveContentTypeAndContent(friendChats.get(i).getContentWithContentType2());
				contentTypes.add(contentWithType2.get(0));
				contents.add(contentWithType2.get(1));
			}
			chatModel.setContent(contents);
			chatModel.setContentType(contentTypes);

			chatModel.setStatus(friendChats.get(i).getStatus());
			chats.add(chatModel);
		}

		return chats;
	}
	

	
	
	
	
	
	@PostMapping(value = "previousChatsWithoutId")
	public List<ChatModel> mostPreviousChat(
			@RequestParam(value = "friendEamil", required = true) String friendEamil, 
			HttpServletResponse response,
			Principal principal) throws IOException {
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		User friendUser = userService.getUsersByEmail(friendEamil);
		//if user not exist
		if(friendUser==null) {
			response.sendError(404, "user not found");
			return null;
		}
		//if user is not friend
		if(!friendService.isUserFriend(loggedInUser, friendUser)) {
			response.sendError(404, "user is not your friend");
			return null;
		}

		List<FriendChat> friendChats = friendChatService.priviousChatsWithoutId(loggedInUser, friendUser);
		
		List<ChatModel> chats = new ArrayList<>();
		for(int i=0; i<friendChats.size();i++) {
			User receiver = friendChats.get(i).getFriend2ChatId();
			User sender = friendChats.get(i).getFriend1ChatId();
			
			ChatModel chatModel = new ChatModel();
			chatModel.setChatId(friendChats.get(i).getFriendChatId());
			chatModel.setFrom(sender.getEmail());
			chatModel.setTo(receiver.getEmail());
			chatModel.setTimeStamp(friendChats.get(i).getDateAndTime());
			chatModel.setFirstName(sender.getFirstName());
			chatModel.setLastName(sender.getLastName());
			chatModel.setPhotoUrl(sender.getPhotoUrl());
			if(sender.equals(loggedInUser)) {
				chatModel.setOwn(true);
			}else {
				chatModel.setOwn(false);
			}

			List<String> contentTypes= new ArrayList<>();
			List<String> contents = new ArrayList<>();
			List<String> contentWithType1 = giveContentTypeAndContent(friendChats.get(i).getContentWithContentType1());
			contentTypes.add(contentWithType1.get(0));
			contents.add(contentWithType1.get(1));

			if(friendChats.get(i).getContentWithContentType2()!=null && !friendChats.get(i).getContentWithContentType2().isEmpty()) {
				List<String> contentWithType2 = giveContentTypeAndContent(friendChats.get(i).getContentWithContentType2());
				contentTypes.add(contentWithType2.get(0));
				contents.add(contentWithType2.get(1));
			}
			chatModel.setContent(contents);
			chatModel.setContentType(contentTypes);

			chatModel.setStatus(friendChats.get(i).getStatus());
			chats.add(chatModel);
		}

		return chats;
	}

	
	
	
	
	
	
	
	
	
	
	@PostMapping(value = "unreadMessage")
	public List<ChatModel> unreadChats(HttpServletResponse response, Principal principal) {
		
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		
		//all the messages sent to client with status of 1 or 2
		List<FriendChat> unreadFreindChats = friendChatService.getUnreadMessageByUser(loggedInUser);
		
		List<ChatModel> chats = new ArrayList<>();
		for(int i=0; i<unreadFreindChats.size();i++) {
			User sender = unreadFreindChats.get(i).getFriend1ChatId();
			User receiver = unreadFreindChats.get(i).getFriend2ChatId();

			ChatModel chatModel = new ChatModel();
			chatModel.setChatId(unreadFreindChats.get(i).getFriendChatId());
			chatModel.setFrom(sender.getEmail());
			chatModel.setTo(receiver.getEmail());
			chatModel.setTimeStamp(unreadFreindChats.get(i).getDateAndTime());
			chatModel.setFirstName(sender.getFirstName());
			chatModel.setLastName(sender.getLastName());
			chatModel.setPhotoUrl(sender.getPhotoUrl());
			chatModel.setOwn(false);			
			chatModel.setStatus(unreadFreindChats.get(i).getStatus());
			List<String> contentTypes= new ArrayList<>();
			List<String> contents = new ArrayList<>();
			List<String> contentWithType1 = giveContentTypeAndContent(unreadFreindChats.get(i).getContentWithContentType1());
			contentTypes.add(contentWithType1.get(0));
			contents.add(contentWithType1.get(1));
			if(unreadFreindChats.get(i).getContentWithContentType2()!=null && !unreadFreindChats.get(i).getContentWithContentType2().isEmpty()) {
				List<String> contentWithType2 = giveContentTypeAndContent(unreadFreindChats.get(i).getContentWithContentType2());
				contentTypes.add(contentWithType2.get(0));
				contents.add(contentWithType2.get(1));
				
//				contents.add(unreadFreindChats.get(i).getContentWithContentType2());
			}
			chatModel.setContent(contents);
			chatModel.setContentType(contentTypes);

			chats.add(chatModel);

		}

		return chats;
	}
	
	@PostMapping("messageReceivedConformation")
	public List<Integer> messageReceivedConformation(@RequestBody List<Integer> chatIds, HttpServletResponse response, Principal principal) {
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		List<Integer> updatedStatusChatIds = new ArrayList<>();
		for(int i=0; i<chatIds.size(); i++) {
			Optional<FriendChat> chat = friendChatService.getFriendChatById(chatIds.get(i));
			//if chat exist	//if chat is received by user //if status is less than 2
			if(chat.isPresent() && chat.get().getFriend2ChatId().equals(loggedInUser) && chat.get().getStatus()<2) {
				chat.get().setStatus(2);
				friendChatService.updateFriendChat(chat.get());
				updatedStatusChatIds.add(chatIds.get(i));
				this.simpMessagingTemplate.convertAndSend("/topic/received/"+chat.get().getFriend1ChatId().getEmail(), chatIds.get(i));
			}
		}
		return updatedStatusChatIds;
	}
	
	@PostMapping("messageReadConformation")
	public List<Integer> messageReadConformation(@RequestBody List<Integer> chatIds, HttpServletResponse response, Principal principal) {
		User loggedInUser = userService.getUsersByEmail(principal.getName());
		List<Integer> updatedStatusChatIds = new ArrayList<>();
		for(int i=0; i<chatIds.size(); i++) {
			Optional<FriendChat> chat = friendChatService.getFriendChatById(chatIds.get(i));
			//if chat exist	//if chat is received by user //if status is less than 3
			if(chat.isPresent() && chat.get().getFriend2ChatId().equals(loggedInUser) && chat.get().getStatus()<3) {
				chat.get().setStatus(3);
				friendChatService.updateFriendChat(chat.get());
				updatedStatusChatIds.add(chatIds.get(i));
				this.simpMessagingTemplate.convertAndSend("/topic/read/"+chat.get().getFriend1ChatId().getEmail(), chatIds.get(i));
			}
		}
		return updatedStatusChatIds;
	}
	
	@GetMapping("video/{fileName}")
	public final ResponseEntity<InputStreamResource> serveVideo(@PathVariable String fileName) throws Exception {
		ClassPathResource videoFile = new ClassPathResource("static/video/"+fileName+".mp4");
		InputStream targetStream  = videoFile.getInputStream();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.valueOf("video/mp4"));
	    headers.set("Accept-Ranges", "bytes");
	    headers.set("Expires", "0");
	    headers.set("Cache-Control", "no-cache, no-store");
	    headers.set("Connection", "keep-alive");
	    headers.set("Content-Transfer-Encoding", "binary");
	    return new ResponseEntity<>(new InputStreamResource(targetStream), headers, HttpStatus.OK);
	}

    

    
    
////	  populating data in table
//    @GetMapping("resetTableData")
//    public String populateData() throws IOException, ParseException {
//    	
//    	boolean onlyDeleteResourcesAndTableData=true;
//    	
//    	//deleting previous database and file system resources
//    	userService.deleteAllUsers();
//        resourceService.deleteAllImages();
//        resourceService.deleteAllVideos();
//        
//        if(onlyDeleteResourcesAndTableData) {
//        	return "only deleted file and table data";
//        }
//    	
//        //all the method scoped variables
//    	List<User> users = new ArrayList<>();
//    	List<Friend> friends = new ArrayList<>();
//    	List<FriendChat> friendChats = new ArrayList<>();
//    	List<FriendRequest> friendRequests = new ArrayList<>();
//    	List<Post> posts = new ArrayList<>();
//    	List<Likes> likes = new ArrayList<>();
//    	List<Comments> comments = new ArrayList<>();
//    	
//    	List<String> gender = new ArrayList<>();
//    	gender.add("male");
//    	gender.add("female");
//    	List<Boolean> readed = new ArrayList<>();
//    	readed.add(true);
//    	readed.add(false);
//    	
//    	
//		String sDate1 = "27/09/1995";
////		  java.util.Date utilDate = new java.util.Date();		//for current date
//		java.util.Date utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
//		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
////		java.sql.Time sqlTime = new java.sql.Time(utilDate.getTime());
//		java.sql.Timestamp sqlTS = new java.sql.Timestamp(utilDate.getTime());
//
//
//
//
//		//saving data in user table
//		
//    	for(int i =0; i<200; i++) {
//    		User user = new User();
//    		user.setFirstName("user"+i);
//    		user.setLastName("user"+i+"last");
//    		user.setEmail("user"+i+"@gmail.com");
//    		user.setPassword("p");
//    		user.setPhotoUrl(resourceService.setDefaultImage());
//    		user.setAddress("user"+i+"address in Mumbai");
//    		user.setPhone("user 1 phone number");
//    		user.setGender(gender.get((int)Math.floor(Math.random()*(2))));
//    		user.setDob(sqlDate);
//    		user.setJobInfo("user"+i+" job info fsf sfs sfdsfds dfsdfds fdsfsdfsd fdsfsdfsdfsdfsdfsfsdfsf sfdsfd sdfsdfsdf sdfsdfsdf  fsfs fssfdsfsd  fdsfdsf fsdfsd fsdf f sdfsfs fs fsd sfs fsdf dsfs fdsf sf sfsfsfs  fsdf sdfds fsdf sdf sdfsdf ");
//    		user.setAcademicInfo("user"+i+" academic info fsf sfs sfdsfds dfsdfds fdsfsdfsd fdsfsdfsdfsdfsdfsfsdfsf sfdsfd sdfsdfsdf sdfsdfsdf  fsfs fssfdsfsd  fdsfdsf fsdfsd fsdf f sdfsfs fs fsd sfs fsdf dsfs fdsf sf sfsfsfs  fsdf sdfds fsdf s");
//    		user.setPersonalInfo("user"+i+" personal info fsf sfs sfdsfds dfsdfds fdsfsdfsd fdsfsdfsdfsdfsdfsfsdfsf sfdsfd sdfsdfsdf sdfsdfsdf  fsfs fssfdsfsd  fdsfdsf fsdfsd fsdf f sdfsfs fs fsd sfs fsdf dsfs fdsf sf sfsfsfs  fsdf sdfds fsdf s");
//    		user.setLastAppearance(sqlTS);
//    		users.add(user);
//    	}
//    	
//    	userService.saveAllUsers(users);
//    	
////    	saving data in friend table and friend chat table
//    	for(int i=0; i<6; i++) {
//    			if(i==0) {
//    				Friend friend1 = new Friend();
//    				friend1.setDateAndTime(sqlTS);
//    				friend1.setFriend1Id(users.get(i));
//    				friend1.setFriend2Id(users.get(i+1));
//    				friends.add(friend1);
//    				
//    				for(int j=0; j<50; j++) {
//    					FriendChat friendChat1=new FriendChat();
//    					friendChat1.setFriend1ChatId(users.get(i));
//    					friendChat1.setFriend2ChatId(users.get(i+1));
//    					friendChat1.setDateAndTime(sqlTS);
//    					friendChat1.setChatMessage("message "+j+" sent from "+users.get(i).getFirstName()+" to "+users.get(i+1).getFirstName());
//    					friendChat1.setReaded(readed.get((int)Math.floor(Math.random()*(2))));
//    					int randomNumber = (int)Math.floor(Math.random()*(3));
//    					if(randomNumber==0) {
//    						friendChat1.setChatVideoUrl(resourceService.setDefaultVideo());
//    					}else if(randomNumber==1) {
//    						friendChat1.setChatImageUrl(resourceService.setDefaultImage());
//    					}
//
//    					FriendChat friendChat2=new FriendChat();
//    					friendChat2.setFriend1ChatId(users.get(i+1));
//    					friendChat2.setFriend2ChatId(users.get(i));
//    					friendChat2.setDateAndTime(sqlTS);
//    					friendChat2.setChatMessage("message "+j+" sent from "+users.get(i+1).getFirstName()+" to "+users.get(i).getFirstName());
//    					friendChat2.setReaded(readed.get((int)Math.floor(Math.random()*(2))));
//    					 randomNumber = (int)Math.floor(Math.random()*(3));
//    					if(randomNumber==0) {
//    						friendChat1.setChatVideoUrl(resourceService.setDefaultVideo());
//    					}else if(randomNumber==1) {
//    						friendChat1.setChatImageUrl(resourceService.setDefaultImage());
//    					}
//
//    					friendChats.add(friendChat1);
//    					friendChats.add(friendChat2);
//    					
//    				}
//    				
//    				
//    				Friend friend2 = new Friend();
//    				friend2.setDateAndTime(sqlTS);
//    				friend2.setFriend1Id(users.get(i));
//    				friend2.setFriend2Id(users.get(5));
//    				friends.add(friend2);
//
//    				for(int j=0; j<50; j++) {
//    					FriendChat friendChat1=new FriendChat();
//    					friendChat1.setFriend1ChatId(users.get(i));
//    					friendChat1.setFriend2ChatId(users.get(5));
//    					friendChat1.setDateAndTime(sqlTS);
//    					friendChat1.setChatMessage("message "+j+" sent from "+users.get(i).getFirstName()+" to "+users.get(5).getFirstName());
//    					friendChat1.setReaded(readed.get((int)Math.floor(Math.random()*(2))));
//    					int randomNumber = (int)Math.floor(Math.random()*(3));
//    					if(randomNumber==0) {
//    						friendChat1.setChatVideoUrl(resourceService.setDefaultVideo());
//    					}else if(randomNumber==1) {
//    						friendChat1.setChatImageUrl(resourceService.setDefaultImage());
//    					}
//
//    					FriendChat friendChat2=new FriendChat();
//    					friendChat2.setFriend1ChatId(users.get(5));
//    					friendChat2.setFriend2ChatId(users.get(i));
//    					friendChat2.setDateAndTime(sqlTS);
//    					friendChat2.setChatMessage("message "+j+" sent from "+users.get(5).getFirstName()+" to "+users.get(i).getFirstName());
//    					friendChat2.setReaded(readed.get((int)Math.floor(Math.random()*(2))));
//    					randomNumber = (int)Math.floor(Math.random()*(3));
//    					if(randomNumber==0) {
//    						friendChat1.setChatVideoUrl(resourceService.setDefaultVideo());
//    					}else if(randomNumber==1) {
//    						friendChat1.setChatImageUrl(resourceService.setDefaultImage());
//    					}
//	
//    					friendChats.add(friendChat1);
//    					friendChats.add(friendChat2);
//    					
//    				}
//    		}else {
//    			Friend friend = new Friend();
//    			friend.setDateAndTime(sqlTS);
//    			friend.setFriend1Id(users.get(i));
//    			friend.setFriend2Id(users.get(i+1));
//    			friends.add(friend);
//    			
//				for(int j=0; j<50; j++) {
//					FriendChat friendChat1=new FriendChat();
//					friendChat1.setFriend1ChatId(users.get(i));
//					friendChat1.setFriend2ChatId(users.get(i+1));
//					friendChat1.setDateAndTime(sqlTS);
//					friendChat1.setChatMessage("message "+j+" sent from "+users.get(i).getFirstName()+" to "+users.get(i+1).getFirstName());
//					friendChat1.setReaded(readed.get((int)Math.floor(Math.random()*(2))));
//					int randomNumber = (int)Math.floor(Math.random()*(3));
//					if(randomNumber==0) {
//						friendChat1.setChatVideoUrl(resourceService.setDefaultVideo());
//					}else if(randomNumber==1) {
//						friendChat1.setChatImageUrl(resourceService.setDefaultImage());
//					}
//
//					FriendChat friendChat2=new FriendChat();
//					friendChat2.setFriend1ChatId(users.get(i+1));
//					friendChat2.setFriend2ChatId(users.get(i));
//					friendChat2.setDateAndTime(sqlTS);
//					friendChat2.setChatMessage("message "+j+" sent from "+users.get(i+1).getFirstName()+" to "+users.get(i).getFirstName());
//					friendChat2.setReaded(readed.get((int)Math.floor(Math.random()*(2))));
//					randomNumber = (int)Math.floor(Math.random()*(3));
//					if(randomNumber==0) {
//						friendChat1.setChatVideoUrl(resourceService.setDefaultVideo());
//					}else if(randomNumber==1) {
//						friendChat1.setChatImageUrl(resourceService.setDefaultImage());
//					}
//					
//					friendChats.add(friendChat1);
//					friendChats.add(friendChat2);
//					
//				}
//
//    		}
//    		for(int j=7; j<60; j++) {
//    			Friend friend = new Friend();
//    			friend.setDateAndTime(sqlTS);
//    			friend.setFriend1Id(users.get(i));
//    			friend.setFriend2Id(users.get(j));
//    			friends.add(friend);
//    		}
//    	}
//    	
//    	friendService.saveAllFriends(friends);
////    	friendChatService.saveAllFriendChats(friendChats);
//    	
////    	saving data in friend request table
//    	for(int i=0; i<6; i++) {
//    		for(int j=61; j<110; j++) {
//    			FriendRequest friendRequest = new FriendRequest();
//    			friendRequest.setDateAndTime(sqlTS);
//    			friendRequest.setFriend1RequestId(users.get(i));
//    			friendRequest.setFriend2RequestId(users.get(j));
//    			friendRequest.setReaded(readed.get((int)Math.floor(Math.random()*(2))));
//    			friendRequests.add(friendRequest);
//    		}
//    		
//    		for(int j=111; j<160; j++) {
//    			FriendRequest friendRequest = new FriendRequest();
//    			friendRequest.setDateAndTime(sqlTS);
//    			friendRequest.setFriend1RequestId(users.get(j));
//    			friendRequest.setFriend2RequestId(users.get(i));
//    			friendRequest.setReaded(readed.get((int)Math.floor(Math.random()*(2))));
//    			friendRequests.add(friendRequest);
//    		}
//    		
//    	}
//    	
//    	friendRequestService.saveAllFriendRequests(friendRequests);
//    	
//
////    	saving data in post table
//    	for(int i=0; i<6; i++) {
//    		
//    		for(int j=0; j<100; j++) {
//        		Post post = new Post();
//        		post.setUserId(users.get(i));
//        		post.setDateAndTime(sqlTS);
//        		post.setTextMessage(users.get(i).getFirstName()+" post number "+j);
//        		
//				int randomNumberForResource = (int)Math.floor(Math.random()*(3));
//				if(randomNumberForResource==0) {
//					post.setImageUrl(resourceService.setDefaultImage());
//				}else if(randomNumberForResource==1) {
//					post.setVideoUrl(resourceService.setDefaultVideo());
//				}
//				
//				int randomNumberForAudience = (int)Math.floor(Math.random()*(3));
//				if(randomNumberForAudience==0) {
//					post.setAudience("PUBLIC");
//				}else if(randomNumberForAudience==1) {
//					post.setAudience("PRIVATE");
//				}else{
//					post.setAudience("FRIEND");
//				}
//				posts.add(post);
//    		}
//    	}
//    	postService.saveAllPosts(posts);
//    	
//    	
////    	saving data in like table
//    	for(int i=0; i<6; i++) {
//    		List<Post> likeAllowedPostList = new ArrayList<>();
//    		for(int j=0; j<posts.size(); j++) {
//    			if(!posts.get(j).getAudience().equalsIgnoreCase("private") || posts.get(j).getUserId().equals(users.get(i))) {
//    				likeAllowedPostList.add(posts.get(j));
//    			} 
//    		}
//    		
//    		Collections.shuffle(likeAllowedPostList);
//
//    		for(int j=0; j<likeAllowedPostList.size(); j++) {
//    			if(j<100) {
//        			Likes like = new Likes();
//        			like.setDateAndTime(sqlTS);
//    				int randomLike = (int)Math.floor(Math.random()*(2));
//    				if(randomLike==0) {
//            			like.setLiked(true);
//    				}else{
//            			like.setLiked(false);
//    				}
//        			like.setPostId(likeAllowedPostList.get(j));
//        			like.setUserId(users.get(i));
//        			likes.add(like);
//    			}
//    		}
//    	}
//    	likesService.saveAllLikes(likes);
//    	
////    	saving data in comment table
//    	for(int i=0; i<6; i++) {
//    		List<Post> commentsAllowedPostList = new ArrayList<>();
//    		for(int j=0; j<posts.size(); j++) {
//    			if(!posts.get(j).getAudience().equalsIgnoreCase("private") || posts.get(j).getUserId().equals(users.get(i))) {
//    				commentsAllowedPostList.add(posts.get(j));
//    			} 
//    		}
//    		
//    		Collections.shuffle(commentsAllowedPostList);
//
//    		for(int j=0; j<commentsAllowedPostList.size(); j++) {
//    			if(j<100) {
//    				
////    				int numberOfComments = (int)Math.floor(Math.random()*(3));
//    				int numberOfComments = (int)Math.floor(Math.random()*(13));
//    				
//    				for(int k=0; k<=numberOfComments;k++) {
//        				Comments comment = new Comments();
//        				comment.setDateAndTime(sqlTS);
//        				comment.setPostId(commentsAllowedPostList.get(j));
//        				comment.setUserId(users.get(i));
//        				comment.setTextMessage(users.get(i).getFirstName()+" comment number "+k+" to "+commentsAllowedPostList.get(j).getUserId().getFirstName()+" post of id "+commentsAllowedPostList.get(j).getPostId());
////        				comment.setTextMessage("user 1 comment 1 to user 2 post postid");
//    					comments.add(comment);
//    				}
//    				
//
//    			}
//    		}
//    	}
//    	commentsService.saveAllComments(comments);
//
//    	return "success";
//    }
    
    List<String> giveContentTypeAndContent(String data){
    	ArrayList<String> output = new ArrayList<>();
    		String[] parts = data.split(" ", 2);
    		output.add(parts[0]);
    		output.add(parts[1]);
    		return output;

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
