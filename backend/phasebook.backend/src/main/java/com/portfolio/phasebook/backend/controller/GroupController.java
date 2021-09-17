package com.portfolio.phasebook.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.phasebook.backend.entity.Group;
import com.portfolio.phasebook.backend.entity.GroupMember;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.resources.ResourcesService;
import com.portfolio.phasebook.backend.service.GroupMemberService;
import com.portfolio.phasebook.backend.service.GroupService;
import com.portfolio.phasebook.backend.service.UserService;




@RestController
@RequestMapping(value = "group")
public class GroupController {
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ResourcesService resourcesService;

	@Autowired
	GroupMemberService groupMemberService;
	
	//create group
	@PostMapping(value = "createGroup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Group createGroup(
			@Valid @ModelAttribute Group group, 
			@RequestParam(value = "image", required = false) MultipartFile imageFile,
			HttpServletResponse response,
			Principal principal) throws IOException, InterruptedException {
		
		User user = userService.getUsersByEmail(principal.getName());
		group.setCreatorId(user);
		
		if(imageFile!=null) {
			String imageName = resourcesService.createResource(imageFile, "jpg");
			group.setGroupImageUrl(imageName);
		}

		Group myGroup = groupService.saveGroup(group);
		
		GroupMember groupMember = new GroupMember();
		groupMember.setMemberUserId(user);
		groupMember.setSelectedGroupId(myGroup);
		groupMemberService.saveGroupMember(groupMember);
		return myGroup;
	}
	
	//update group
	@PostMapping(value = "updateGroup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Group updateGroup(
			@Valid @ModelAttribute Group group, 
			@RequestParam(value = "image", required = false) MultipartFile imageFile,
			HttpServletResponse response,
			Principal principal) throws IOException, InterruptedException {

		Optional<Group> userGroup = groupService.getGroupById(group.getGroupId());
		userGroup.get().setGroupName(group.getGroupName());
		userGroup.get().setGroupDescription(group.getGroupDescription());
		if(imageFile!=null) {
			String imageName = resourcesService.createResource(imageFile, "jpg");
			if(userGroup.get().getGroupImageUrl()!=null) {
				resourcesService.deleteResource(userGroup.get().getGroupImageUrl(), "jpg");	//error
			}
			userGroup.get().setGroupImageUrl(imageName);
		}
		return groupService.updateGroup(userGroup.get());
	}

	//delete group
	@PostMapping(value = "deleteGroup")
	public void deleteGroup(@RequestParam(value = "groupId", required = true) Integer id) {
		groupService.deleteGroup(groupService.getGroupById(id).get());
	}
}
