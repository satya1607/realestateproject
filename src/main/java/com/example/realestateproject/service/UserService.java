package com.example.realestateproject.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.realestateproject.entity.User;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.exception.UserNotFoundException;
import com.example.realestateproject.repository.UserInfoRepository;
import com.example.realestateproject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	public void saveUser(UserInfo user) {
		userInfoRepository.save(user);
	}
    
	public List<UserInfo> displayUsers() {
		List<UserInfo> result=userInfoRepository.findAll();
		return result;
	}
	 public UserInfo getUserById(ObjectId id) {
	        return userInfoRepository.findById(id)
	            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
	    }
	 
	public void deleteUser(ObjectId id) {
		userInfoRepository.deleteById(id);
	}
	
	public UserInfo editUser(ObjectId id,UserInfo user) {
		UserInfo newUser=userInfoRepository.findById(id).orElse(null);
		if(newUser == null) {
		   return null;
		}
		user.setId(id);
		return userInfoRepository.save(user);
	}
}
