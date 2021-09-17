package com.portfolio.phasebook.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.Friend;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.repository.FriendRepository;

@Service
@Transactional
public class FriendService {
	
	@Autowired
	FriendRepository friendrepository;
	
	public Optional<Friend> getFriendById(Integer  id) {
		return friendrepository.findById(id);
	}
	
	public Friend saveFriend(Friend friend) {
		return friendrepository.save(friend);
	}
	
	public Friend updateFriend(Friend friend) {
		return friendrepository.save(friend);
	}
	
	public void deleteFriend(Friend friend) {
		friendrepository.delete(friend);
	}
	
	//it will take 2 id inputs and check those two are friends or not and return true or false
	
	public boolean isUserFriend(User user1, User user2) {
		if(friendrepository.existsByFriend1IdAndFriend2Id(user1, user2)) {
			return true;
		}else if(friendrepository.existsByFriend1IdAndFriend2Id(user2, user1)) {
			return true;
		}
		return false;
	}
	
	
	public void saveAllFriends(List<Friend> friends) {
		friendrepository.saveAll(friends);
	}
	
	public List<Friend> getUserFriends(User user, int pageNumber){
		return friendrepository.findByFriend1IdOrFriend2Id(user, user, PageRequest.of(pageNumber, 20, Sort.by("friendId").descending())).toList();
	}

	public void unFriend(User loggedInUser, User unFriendingUser) {
		friendrepository.deleteByFriend1IdAndFriend2Id(loggedInUser, unFriendingUser);
		friendrepository.deleteByFriend1IdAndFriend2Id(unFriendingUser, loggedInUser);
	}

	public List<Friend> getAllUserFriends(User usersByEmail) {
		return friendrepository.findByFriend1IdOrFriend2Id(usersByEmail, usersByEmail);
	}

	

	
	

}
