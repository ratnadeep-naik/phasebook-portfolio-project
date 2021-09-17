package com.portfolio.phasebook.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.Group;
import com.portfolio.phasebook.backend.repository.GroupRepository;

@Service
@Transactional
public class GroupService {
	
	@Autowired
	GroupRepository groupRepository ;
	
	public Optional<Group> getGroupById(Integer  id) {
		return groupRepository.findById(id);
	}
	
	public Group saveGroup(Group group) {
		return groupRepository.save(group);
	}
	
	public Group updateGroup(Group group) {
		return groupRepository.save(group);
	}
	
	public void deleteGroup(Group group) {
		groupRepository.delete(group);
	}
	
	public void saveAllGroups(List<Group> groups) {
		groupRepository.saveAll(groups);
	}


}
