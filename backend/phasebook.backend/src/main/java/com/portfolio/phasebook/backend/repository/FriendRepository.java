package com.portfolio.phasebook.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.phasebook.backend.entity.Friend;
import com.portfolio.phasebook.backend.entity.User;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
	boolean existsByFriend1IdAndFriend2Id(User user1, User User2);
	
	Page<Friend> findByFriend1IdOrFriend2Id(User user1,User user2, org.springframework.data.domain.Pageable pageable);
	Long deleteByFriend1IdAndFriend2Id(User user1, User use2);

	List<Friend> findByFriend1IdOrFriend2Id(User usersByEmail, User usersByEmail2);

//	Page<Post> findByFriend1IdOrFriend2IdAndFriend2IdOrFriend1Id(User user,String puplic, org.springframework.data.domain.Pageable pageable);
//	Page<Post> findByFriend1IdOrFriend2Id(User user,String puplic, org.springframework.data.domain.Pageable pageable);


}
