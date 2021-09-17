package com.portfolio.phasebook.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.FriendRequest;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.repository.FriendRequestRepository;

@Service
@Transactional
public class FriendRequestService {
	
	@Autowired
	FriendRequestRepository friendRequestRepository;
	
	public Optional<FriendRequest> getFriendRequestById(Integer  id) {
		return friendRequestRepository.findById(id);
	}
	
	public FriendRequest saveFriendRequest(FriendRequest friendRequest) {
		return friendRequestRepository.save(friendRequest);
	}
	
	public FriendRequest updateFriendRequest(FriendRequest friendRequest) {
		return friendRequestRepository.save(friendRequest);
	}
	
	public void deleteFriendRequest(FriendRequest friendRequest) {
		friendRequestRepository.delete(friendRequest);
	}

	public void saveAllFriendRequests(List<FriendRequest> friendRequests) {
		friendRequestRepository.saveAll(friendRequests);
	}
	
	public boolean isFriendRequestSent(User friendRequestSender, User friendRequestReceiver) {
		return friendRequestRepository.existsByFriend1RequestIdAndFriend2RequestId(friendRequestSender, friendRequestReceiver);
	}

	public List<FriendRequest> getFriendRequestSentUsers(User usersByEmail, int pageNumber) {
		return friendRequestRepository.findByFriend1RequestId(usersByEmail, PageRequest.of(pageNumber, 20, Sort.by("friendRequestId").descending())).toList();
	}
	
	public List<FriendRequest> getFriendRequestReceivedUsers(User usersByEmail, int pageNumber) {
		return friendRequestRepository.findByFriend2RequestId(usersByEmail, PageRequest.of(pageNumber, 20, Sort.by("friendRequestId").descending())).toList();
	}

	public long deleteFriendRequest(User loggedInUser, User friendRequestCancellingUser) {
		return friendRequestRepository.deleteByFriend1RequestIdAndFriend2RequestId(loggedInUser, friendRequestCancellingUser);
		
	}
	

	

}
