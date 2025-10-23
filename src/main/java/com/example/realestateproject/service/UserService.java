package com.example.realestateproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.realestateproject.entity.User;
import com.example.realestateproject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
    
	public List<User> displayUsers() {
		List<User> result=userRepository.findAll();
		return result;
	}
	
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	
	public User editUser(Integer id,User user) {
		User newUser=userRepository.findById(id).orElse(null);
		if(newUser == null) {
		   return null;
		}
		user.setId(id);
		return userRepository.save(user);
	}
}
