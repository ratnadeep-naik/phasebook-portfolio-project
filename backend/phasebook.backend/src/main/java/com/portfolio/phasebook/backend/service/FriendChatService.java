package com.portfolio.phasebook.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.FriendChat;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.repository.FriendChatRepository;

@Service
@Transactional
public class FriendChatService {

	@Autowired
	FriendChatRepository friendChatRepository;
	
	
	public Optional<FriendChat> getFriendChatById(Integer  id) {
		
		return friendChatRepository.findById(id);
	}
	
	public FriendChat saveFriendChat(FriendChat friendChat) {
		return friendChatRepository.save(friendChat);
	}
	
	public FriendChat updateFriendChat(FriendChat friendChat) {
		return friendChatRepository.save(friendChat);
	}
	
	public void deleteFriendChat(FriendChat friendChat) {
		friendChatRepository.delete(friendChat);
	}
	
	public void saveAllFriendChats(List<FriendChat> friendChats) {
		friendChatRepository.saveAll(friendChats);
	}

	public List<FriendChat> userChatsByFriendLastMessageIdAndQuantity(User loggedInUser, User friendUser, int earliestChatId, int rowQuantity) {
		ArrayList<User> users = new ArrayList<>();
		users.add(loggedInUser);
		users.add(friendUser);
		return friendChatRepository.findFirst20ByFriend1ChatIdInAndFriend2ChatIdInAndFriendChatIdLessThanOrderByFriendChatIdDesc(users, users, earliestChatId); //, earliestChatId //ByOrderByFriendChatIdDesc
	}

	public List<FriendChat> getUnreadMessageByUser(User loggedInUser) {
		// TODO Auto-generated method stub
		ArrayList<Integer> status = new ArrayList<>();
		status.add(1);
		status.add(2);
		return friendChatRepository.findByFriend2ChatIdAndStatusInOrderByFriendChatIdDesc(loggedInUser, status);
	}

	public List<FriendChat> priviousChatsWithoutId(User loggedInUser, User friendUser) {
		ArrayList<User> users = new ArrayList<>();
		users.add(loggedInUser);
		users.add(friendUser);
		return friendChatRepository.findFirst20ByFriend1ChatIdInAndFriend2ChatIdInOrderByFriendChatIdDesc(users, users);
	}




}
