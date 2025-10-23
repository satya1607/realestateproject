package com.example.realestateproject.com.example.realestateproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework
.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.realestateproject.entity.User;
import com.example.realestateproject.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
@PostMapping("/admin/user/save")
public void saveUser(@RequestBody User user) {
	userService.saveUser(user);
}

@GetMapping("/admin/user/display")
public List<User> displayUsers() {
	return userService.displayUsers();
}

@PutMapping("/admin/user/edit/{id}")
public User editUser(@PathVariable Integer id,@RequestBody User user) {
	return userService.editUser(id,user);
}

@DeleteMapping("/admin/user/delete/{id}")
public void deleteUser(@PathVariable Integer id) {
	userService.deleteUser(id);
}
}
