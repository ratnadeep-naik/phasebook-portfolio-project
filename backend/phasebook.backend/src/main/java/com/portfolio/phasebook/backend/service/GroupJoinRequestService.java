package com.portfolio.phasebook.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.GroupJoinRequest;
import com.portfolio.phasebook.backend.repository.GroupJoinRequestRepository;

@Service
@Transactional
public class GroupJoinRequestService {
	
	@Autowired
	GroupJoinRequestRepository groupJoinRequestRepository ;
	
	public Optional<GroupJoinRequest> getGroupJoinRequestById(Integer  id) {
		return groupJoinRequestRepository.findById(id);
	}
	
	public GroupJoinRequest saveGroupJoinRequest(GroupJoinRequest groupJoinRequest) {
		return groupJoinRequestRepository.save(groupJoinRequest);
	}
	
	public GroupJoinRequest updateGroupJoinRequest(GroupJoinRequest groupJoinRequest) {
		return groupJoinRequestRepository.save(groupJoinRequest);
	}
	
	public void deleteGroupJoinRequest(GroupJoinRequest groupJoinRequest) {
		groupJoinRequestRepository.delete(groupJoinRequest);
	}

	public void saveAllGroupJoinRequests(List<GroupJoinRequest> groupJoinRequests) {
		groupJoinRequestRepository.saveAll(groupJoinRequests);
	}

}
