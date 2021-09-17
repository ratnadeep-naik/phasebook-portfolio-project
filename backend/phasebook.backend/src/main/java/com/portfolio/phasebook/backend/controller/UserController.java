package com.portfolio.phasebook.backend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.resources.ResourcesService;
import com.portfolio.phasebook.backend.service.UserService;

@RestController
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ResourcesService resourceService;

	//get an user based on email
	@GetMapping("/getuser")
	public User getUser(Principal principal) {
		return userService.getUsersByEmail(principal.getName());
	}

	//create an user
	@PostMapping(value = "createUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public User createUser(
			@Valid @ModelAttribute User user, 
			HttpServletResponse response) throws IOException{
		User isEmailExist = userService.getUsersByEmail(user.getEmail());
		if(isEmailExist==null) {
			return userService.saveUser(user);
			
		}
      response.sendError(400, "email already exist");
		return null;
	}

	//update an user
	@PostMapping(value = "updateUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public User updateUser(
			@Valid @ModelAttribute User userWithoutId, 
			@RequestParam(value = "image", required = false) MultipartFile file, 
			HttpServletResponse response,
			Principal principal) throws IOException, InterruptedException{
		String userEmail = principal.getName();
		User user = userService.getUsersByEmail(userEmail);
		//is user email valid
		if(user.getEmail().equals(userEmail) || userService.getUsersByEmail(user.getEmail())==null) {
			if(!user.getEmail().equals(userEmail)) {
				SecurityContextHolder.clearContext();
			}
			if(file==null) {
				if(userService.getUsersByEmail(userEmail).getPhotoUrl()==null) {
					return userService.updateUser(user);
				}else {
					String fileName = userService.getUsersByEmail(userEmail).getPhotoUrl();
					String extension = "jpg";
					resourceService.deleteResource(fileName, extension);
				}
			}else {
				if(userService.getUsersByEmail(userEmail).getPhotoUrl()==null) {
					String fileName = resourceService.createResource(file, "jpg");
					user.setPhotoUrl(fileName);
					return userService.updateUser(user);
				}else {
					try {
						String deletingFileName = userService.getUsersByEmail(userEmail).getPhotoUrl();
						String extension = "jpg";
						resourceService.deleteResource(deletingFileName, extension);
						//save into resources
						String fileName = resourceService.createResource(file, "jpg");
						user.setPhotoUrl(fileName);
						return userService.updateUser(user);
					} catch (IOException e) {
						e.printStackTrace();
						String fileName = resourceService.createResource(file, "jpg");
						user.setPhotoUrl(fileName);
						return userService.updateUser(user);
					}
				}
			}
		}
		response.sendError(400, "email already exist");
		return null;
	}

	//delete an user
	@PostMapping("deleteUser")
	public void deleteUser(Principal principal){
		SecurityContextHolder.clearContext();
		userService.deleteUser(userService.getUsersByEmail(principal.getName()));
	}

	//sending friend request to appropriate user
	@PostMapping("friendRequestSend")
	public void sendFriendRequest(Principal principal, @RequestParam(value = "userEmail", required = true) String userEmail){
		//user should not be a friend
		
		Integer friendRequestSenderId = userService.getUsersByEmail(principal.getName()).getUserId();
		Integer friendRequestReceiverId = userService.getUsersByEmail(userEmail).getUserId();

		//user should not already receive friend request

		
		//user should not send friend request twice
		
		
		
		
	}
	
							/* entry exit */
	
//	@PostMapping("login")
//	public void login() {
//	}

	@PostMapping("signup")
	public void signup() {
		//email
		//password
		
	}

	@PostMapping("logout")
	public void logout() {
	}

	
	
	@PostMapping("deleteAccount")
	public void deleteAccount() {
	}

	@PostMapping("showProfile")
	public void showProfile() {
	}

	@PostMapping("updateProfile")
	public void updateProfile() {
	}

	@PostMapping("createPost")
	public void createPost() {
	}

	@PostMapping("deletePost")
	public void deletePost() {
	}

	@PostMapping("updatePostAudience")
	public void updatePostAudience() {
	}

	@PostMapping("posts")
	public void posts() {
	}

	@PostMapping("likePost")
	public void likePost() {
	}

	@PostMapping("postCommentUsers")
	public void postCommentUsers() {
	}

	@PostMapping("createComment")
	public void createComment() {
	}

	@PostMapping("deleteComment")
	public void deleteComment() {
	}

	@PostMapping("updateComment")
	public void updateComment() {
	}

	@PostMapping("findFriends")
	public void findFriends() {
	}

	@PostMapping("createFriendRequest")
	public void createFriendRequest() {
	}

	@PostMapping("deleteFriendRequest")
	public void deleteFriendRequest() {
	}

	@PostMapping("showfriendRequestsSent")
	public void showfriendRequestsSent() {
	}

	@PostMapping("showfriendRequestsRecevied")
	public void showfriendRequestsRecevied() {
	}

	@PostMapping("friendRequestAccept")
	public void friendRequestAccept() {
	}

	@PostMapping("myFriends")
	public void myFriends() {
	}


	@PostMapping("unFriend")
	public void unFriend() {
	}

	@PostMapping("createChat")
	public void createChat() {
	}

	@PostMapping("deleteChat")
	public void deleteChat() {
	}

	@PostMapping("showChats")
	public void showChats() {
	}
	
	@PostMapping("createGroup")
	public void createGroup() {
	}

	@PostMapping("updateGroup")
	public void updateGroup() {
	}

	@PostMapping("deleteGroup")
	public void deleteGroup() {
	}

	@PostMapping("findGroups")
	public void findGroups() {
	}

	@PostMapping("groupProfile")
	public void groupProfile() {
	}

	@PostMapping("createGroupJoinRequest")
	public void createGroupJoinRequest() {
	}

	@PostMapping("showGroupJoinRequestsSent")
	public void showGroupJoinRequestsSent() {
	}

	@PostMapping("deleteGroupJoinRequestSent")
	public void deleteGroupJoinRequestSent() {
	}

	@PostMapping("showGroupJoinRequestsReceived")
	public void showGroupJoinRequestsReceived() {
	}

	@PostMapping("groupJoinRequestAccept")
	public void groupJoinRequestAccept() {
	}

	@PostMapping("deleteGroupMember")
	public void deleteGroupMember() {
	}

	@PostMapping("userCreatedGroups")
	public void userCreatedGroups() {
	}

	@PostMapping("userJoinedGroups")
	public void userJoinedGroups() {
	}

	@PostMapping("groupMembers")
	public void groupMembers() {
	}

	@PostMapping("viewGroupChats")
	public void viewGroupChats() {
	}

	@PostMapping("createGroupChat")
	public void createGroupChat() {
	}

	@PostMapping("deleteGroupChat")
	public void deleteGroupChat() {
	}
}
