package com.example.realestateproject.com.example.realestateproject.controller;

import java.util.List;
import java.util.Map;

//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.realestateproject.entity.Register;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.enums.UserRole;
import com.example.realestateproject.repository.UserInfoRepository;
import com.example.realestateproject.repository.UserRepository;
import com.example.realestateproject.service.LoginService;

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
    private PasswordEncoder passwordEncoder;
    
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

	     try {
	         // Fetch user from DB
	         UserInfo dbUser = loginService.login(username);

	         // Check password
	         if (!dbUser.getPassword().equals(password)) {
	             model.addAttribute("error", "Invalid password!");
	             return "login";
	         }

	         // Check role
	         if (!dbUser.getRole().name().equals(role)) {
	             model.addAttribute("error", "Role mismatch!");
	             return "login";
	         }

	         // Save user info in session (optional)
	         session.setAttribute("loggedInUser", dbUser);

	         // Redirect based on role
	         if (dbUser.getRole() == UserRole.ADMIN) {
	             return "redirect:/admin";
	         } else {
	             return "redirect:/user";
	         }

	     } catch (Exception e) {
	         model.addAttribute("error", "User not found!");
	         return "login";
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
//		user.setPassword(passwordEncoder.encode(user.getPassword()));

		loginService.createUser(user);

		return "redirect:/login";
	}
    
    @GetMapping("/user")
    public String showUserDashboard(Model model){
	 
        return "user";
    }
    
    @GetMapping("/admin")
    public String showAdminDashboard(Model model){
	 
        return "admin";
    }
    
//    @GetMapping("/dashboard")
//    public String showDashboard(){
//        return "dashboard";
//    }

//    @GetMapping("/admin/home")
////    @PreAuthorize("hasRole('ADMIN')")
//    public String adminhome(){
//        return "admin";
//    }

//    @GetMapping("/customer/home")
////    @PreAuthorize("hasRole('CUSTOMER')")
//    public String userhome(){
//        return "user";
//    }
	
//	@Autowired
//	private LoginService loginService;
//	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	@Autowired
//	private JWTUtil jwtUtil;
	
//	public record LoginRequest(@NotBlank String username,@NotBlank String password) {
//		
//	}
//	
//	@GetMapping("/")
//	public String login(Model m) {
////		m.addAttribute("login", new Login());
//		return "login";
//	}
	
//	@GetMapping("/hello")
//	public String hello() {
//		User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		System.out.println("Authenticated user:"+user.getUsername()+",Authorities:"
//				+ user.getAuthorities());
//		if(user.getAuthorities().stream().anyMatch(auth->auth.getAuthority().equals
//				("ROLE_ADMIN"))) {
//			System.out.println("Admin user able to access private resources");
//		}else {
//			System.out.println("Regular user detected:limited access");
//		}
//		return "Hello World!";
//	}

	
//    @PostMapping("/login")
//    public String login(@ModelAttribute("login") Login login) {
//    	try {
//    		Authentication auth=authenticationManager.authenticate(new 
//    				UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword()));
//    		String token=jwtUtil.generateToken(auth.getName());
//    		ResponseEntity.ok(Map.of("accessToken",token,"tokenType","Bearer"));
//    		return "home";
//    	}catch(Exception e) {
//    		throw new RuntimeException("Invalid username or password",e);
//    	}
    
//    	String uname=login.getUsername();
//    	String pass=login.getPassword();
//    	if(uname.equals("Admin") && pass.equals("Admin@123")){
//    		m.addAttribute("uname",uname);
//    		m.addAttribute("pass",pass);
//    		return "welcome to dashboard";
//    	}
//    	m.addAttribute("error","Incorrect Username & Password");
//		return "welcome to dashboard";
//    }
//    
//    @GetMapping("/register")
//	public String register(Model m) {
//    	m.addAttribute("register", new Register());
//		return "register";
//	}
//    
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("register") Register register) {
//        loginService.save(register);
//        return "redirect:/register?success";
////        return "redirect:/login";
//    }
    
}
