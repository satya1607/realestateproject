package com.example.realestateproject.com.example.realestateproject.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework
.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.realestateproject.entity.Image;
import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.entity.User;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
@GetMapping("/admin/user/display")
public String displayUsers(Model model) {
	
	List<UserInfo> result=userService.displayUsers();
	List<UserInfo> onlyCustomers = result.stream()
	        .filter(user -> "CUSTOMER".equals(user.getRole().name()))
	        .collect(Collectors.toList());
	System.out.println(onlyCustomers);
	model.addAttribute("onlyCustomers",onlyCustomers);
	return "viewuserdetails";
}

@PutMapping("/admin/user/edit/{id}")
public String editUser(@PathVariable ObjectId id,@ModelAttribute UserInfo user) {
	 userService.editUser(id,user);
	 return "redirect:/admin/user/display";
}
@GetMapping("/admin/user/edit/{id}")
public String showEditForm(@PathVariable ObjectId id, Model model) {
  UserInfo user = userService.getUserById(id);
  model.addAttribute("user", user);
  return "edituserform"; 
}

@GetMapping("/admin/user/delete/{id}")
public String deleteUser(@PathVariable ObjectId id) {
	userService.deleteUser(id);
	return "redirect:/admin/user/display";
}
}
