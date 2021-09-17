package com.portfolio.phasebook.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.phasebook.backend.entity.Friend;
import com.portfolio.phasebook.backend.entity.FriendRequest;
import com.portfolio.phasebook.backend.entity.User;
import com.portfolio.phasebook.backend.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private FriendRequestService friendRequestService;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void deleteAllUsers() {
		repository.deleteAll();
	}

	public List<User> findByUsersLastName() {
		return repository.findByLastName("sameLastName",PageRequest.of(4, 3, Sort.by("userId").descending())).toList();
	}

		public List<User> getAllUsers() {

//		return repository.findAll(PageRequest.of(0, 2, Sort.by("userId").ascending())).toList();
		return repository.findAll();
	}
	
	public User getUsersByEmail(String email) {
		List<User>  users = repository.findFirst1ByEmail(email);
		if(users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
	
	public Optional<User> getUsersById(Integer id) {
//		return repository.getById(id);
		return repository.findById(id);

		
	}

	
	public User saveUser(User user) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			return repository.save(user);
	}
	
//	public User updateUser(User user, String userEmail) {
//		user.setUserId(getUsersByEmail(userEmail).getUserId());
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		return repository.save(user);
//}
	
	
	public User updateUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
}

	
	public void deleteUser(User user) {
		//delete its photo resource in file system
		//delete its post resources in file system
		//delete chat resources in file system associated with this user
		repository.delete(user);
}
	
	public void saveAllUsers(List<User> users) {
		
		for(int i=0; i<users.size(); i++) {
			users.get(i).setPassword(passwordEncoder.encode(users.get(i).getPassword()));
		}
		repository.saveAll(users);
	}

	public List<User> getFriends(User userByEmail, int page) {
		
		List<Friend> userFriendRows = friendService.getUserFriends(userByEmail, page);
		List<User> friends = new ArrayList<>();
		for(int i=0; i<userFriendRows.size();i++) {
			if(userFriendRows.get(i).getFriend1Id().equals(userByEmail)) {
				friends.add(userFriendRows.get(i).getFriend2Id());
			}else {
				friends.add(userFriendRows.get(i).getFriend1Id());
			}
		}
		return friends;
	}

	public List<User> getFriendRequestSentUsers(User usersByEmail, int page) {
		List<FriendRequest> friendRequestRows = friendRequestService.getFriendRequestSentUsers(usersByEmail, page);
		List<User> friendRequestSentUsers = new ArrayList<>();
		for(int i=0; i<friendRequestRows.size(); i++) {
			User user = friendRequestRows.get(i).getFriend2RequestId();
			friendRequestSentUsers.add(user);
		}
		return friendRequestSentUsers;
	}

	public List<User> getFriendRequestReceivedUsers(User usersByEmail, int page) {
		List<FriendRequest> friendRequestReceivedRows = friendRequestService.getFriendRequestReceivedUsers(usersByEmail, page);
		List<User> friendRequestReceivedUsers = new ArrayList<>();
		
		for(int i=0; i<friendRequestReceivedRows.size(); i++) {
			User user = friendRequestReceivedRows.get(i).getFriend1RequestId();
			friendRequestReceivedUsers.add(user);
		}
		
		return friendRequestReceivedUsers;
	}

	public List<User> findUsernameContaining(String keyword, int pageNumber) {
		return repository.findByFirstNameLike(keyword, PageRequest.of(pageNumber, 20, Sort.by("userId").descending())).toList();
	}

	public List<User> getAllFriends(User usersByEmail) {
		
		List<Friend> userFriendRows = friendService.getAllUserFriends(usersByEmail);
		List<User> friends = new ArrayList<>();
		for(int i=0; i<userFriendRows.size();i++) {
			if(userFriendRows.get(i).getFriend1Id().equals(usersByEmail)) {
				friends.add(userFriendRows.get(i).getFriend2Id());
			}else {
				friends.add(userFriendRows.get(i).getFriend1Id());
			}
		}
		return friends;
	}
	


	

	

	

}
