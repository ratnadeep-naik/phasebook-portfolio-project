package com.portfolio.phasebook.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.GroupMember;
import com.portfolio.phasebook.backend.repository.GroupMemberRepository;

@Service
@Transactional
public class GroupMemberService {
	
	@Autowired
	GroupMemberRepository groupMemberRepository ;
	
	public Optional<GroupMember> getGroupMemberById(Integer  id) {
		return groupMemberRepository.findById(id);
	}
	
	public GroupMember saveGroupMember(GroupMember groupMember) {
		return groupMemberRepository.save(groupMember);
	}
	
	public GroupMember updateGroupMember(GroupMember groupMember) {
		return groupMemberRepository.save(groupMember);
	}
	
	public void deleteGroupMember(GroupMember groupMember) {
		groupMemberRepository.delete(groupMember);
	}

	public void saveAllGroupMembers(List<GroupMember> groupMembers) {
		groupMemberRepository.saveAll(groupMembers);
	}

}
