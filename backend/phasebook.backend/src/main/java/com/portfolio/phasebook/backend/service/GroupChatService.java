package com.portfolio.phasebook.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.GroupChat;
import com.portfolio.phasebook.backend.repository.GroupChatRepository;

@Service
@Transactional
public class GroupChatService {
	
	@Autowired
	GroupChatRepository groupChatRepository ;
	
	public Optional<GroupChat> getGroupChatById(Integer  id) {
		return groupChatRepository.findById(id);
	}
	
	public GroupChat saveGroupChat(GroupChat groupChat) {
		return groupChatRepository.save(groupChat);
	}
	
	public GroupChat updateGroupChat(GroupChat groupChat) {
		return groupChatRepository.save(groupChat);
	}
	
	public void deleteGroupChat(GroupChat groupChat) {
		groupChatRepository.delete(groupChat);
	}

	public void saveAllGroupChats(List<GroupChat> groupChats) {
		groupChatRepository.saveAll(groupChats);
	}

	
	

}
