package com.portfolio.phasebook.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.phasebook.backend.entity.FriendRequest;
import com.portfolio.phasebook.backend.entity.User;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
	
	boolean existsByFriend1RequestIdAndFriend2RequestId(User user1, User user2);
	Page<FriendRequest> findByFriend1RequestId(User user, org.springframework.data.domain.Pageable pageable);
	Page<FriendRequest> findByFriend2RequestId(User user, org.springframework.data.domain.Pageable pageable);
	Long deleteByFriend1RequestIdAndFriend2RequestId(User user1, User use2);
}
