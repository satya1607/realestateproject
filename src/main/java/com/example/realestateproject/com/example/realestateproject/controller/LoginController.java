package com.example.realestateproject.com.example.realestateproject.controller;

import java.util.List;
import java.util.Map;

//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.example.quizapplication.entity.TestPOJO;
//import com.example.quizapplication.enums.UserRole;
import com.example.realestateproject.entity.Login;
import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.entity.Register;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.enums.UserRole;
import com.example.realestateproject.repository.UserInfoRepository;
import com.example.realestateproject.repository.UserRepository;
import com.example.realestateproject.service.LoginService;
import com.example.realestateproject.service.PropertyDetailsService;
import com.example.realestateproject.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Controller
public class LoginController {
	
	@Autowired
    private UserInfoRepository repository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PropertyDetailsService service;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }
    
    @PostMapping("/login")
	 public String login(@RequestParam String username,
	                     @RequestParam String password,
	                     @RequestParam String role,
	                     Model model,
	                     HttpSession session) {

	    
	         UserInfo dbUser = loginService.login(username);
	         if (dbUser.getRole() == UserRole.ADMIN) {
	             return "redirect:/admindashboard";
	         } else {
	             return "redirect:/userdashboard";
	         }
	     }
	 
    
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user",new UserInfo());
        return "register";
    }
    
    @PostMapping("/register")
	public String signUpUser(@ModelAttribute UserInfo user){
		System.out.println("Method called");

		loginService.createUser(user);

		return "redirect:/login";
	}
    
    @GetMapping("/admindashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAdminDashboard(Model model){
    	List<PropertyDetails> list = service.getAllProperties();
    	 model.addAttribute("list", list);
        return "admindashboard";
    }

    @GetMapping("/userdashboard")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String userhome(Model model){
    	List<PropertyDetails> list = service.getAllProperties();
   	    model.addAttribute("list", list);
        return "userdashboard";
    }
	

}
