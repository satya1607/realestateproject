package com.example.realestateproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import com.example.quizapplication.entity.User;
//import com.example.quizapplication.exception.UserNotFoundException;
//import com.example.quizapplication.repository.UserRepository;
import com.example.realestateproject.entity.Register;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.exception.UserNotFoundException;
import com.example.realestateproject.repository.RegisterRepository;
import com.example.realestateproject.repository.UserInfoRepository;

@Service
public class LoginService {

	@Autowired
	private RegisterRepository registerRepository;
	
	private final UserInfoRepository userRepository;
	 private final PasswordEncoder passwordEncoder;
	 
	 public LoginService(UserInfoRepository userRepository,
                       PasswordEncoder passwordEncoder) {
           this.userRepository = userRepository;
           this.passwordEncoder = passwordEncoder;
}
	
//	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//	
//	 public void save(Register register) {
//	        register.setPassword(encoder.encode(register.getPassword()));
//	        registerRepository.save(register);
//	    }
	 
	 public void createUser(UserInfo user) {
//			user.setRole(UserRole.USER);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
		}
		
		public UserInfo login(String username) {
			UserInfo user=userRepository.findByUsername(username)
					.orElseThrow(() ->  new UserNotFoundException("User not found in database"));
			
		 return user;
			
		}
}
