package com.portfolio.phasebook.backend.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.portfolio.phasebook.backend.entity.FriendChat;
import com.portfolio.phasebook.backend.entity.User;

public interface FriendChatRepository extends JpaRepository<FriendChat, Integer> {

//	List<FriendChat> findFirst20ByFriend1ChatIdInAndFriend2ChatIdInOrderByFriendChatIdAscByFriendChatIdLessThan(List<User> user1, List<User> user2, Integer i);	//, //, Integer chatId
	List<FriendChat> findFirst20ByFriend1ChatIdInAndFriend2ChatIdInAndFriendChatIdLessThanOrderByFriendChatIdDesc(List<User> user1, List<User> user2, Integer i);	//, //, Integer chatId

	List<FriendChat> findByFriend2ChatIdAndStatusInOrderByFriendChatIdDesc(User user, List<Integer> status);

	List<FriendChat> findFirst20ByFriend1ChatIdInAndFriend2ChatIdInOrderByFriendChatIdDesc(List<User> user1, List<User> user2);

	//InByOrderByFriendChatIdDesc
	

}
