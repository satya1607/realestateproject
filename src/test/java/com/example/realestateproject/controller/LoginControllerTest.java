package com.example.realestateproject.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.example.realestateproject.com.example.realestateproject.controller.LoginController;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.enums.UserRole;
import com.example.realestateproject.repository.UserInfoRepository;
import com.example.realestateproject.repository.UserRepository;
import com.example.realestateproject.service.LoginService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private UserInfoRepository userInfoRepository;

	    @MockBean
	    private UserRepository userRepository;

	    @MockBean
	    private LoginService loginService;

	    @MockBean
	    private PasswordEncoder passwordEncoder;

	    private UserInfo user;

	    @BeforeEach
	    void setUp() {
	        user = new UserInfo();
	        user.setId(1L);
	        user.setUsername("john");
	        user.setPassword("password");
	        user.setRole(UserRole.CUSTOMER);
	    }

	    // ---------- LOGIN PAGE TESTS ----------
	    @Test
	    void testShowLoginForm() throws Exception {
	        mockMvc.perform(get("/login"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.view().name("login"));
	    }

	    @Test
	    void testLoginPostSuccess() throws Exception {
	        when(loginService.login("john")).thenReturn(user);

	        mockMvc.perform(post("/login")
	                        .param("username", "john")
	                        .param("password", "password")
	                        .param("role", "USER"))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

	        verify(loginService, times(1)).login("john");
	    }

	    // ---------- REGISTER PAGE TESTS ----------
	    @Test
	    void testShowRegisterForm() throws Exception {
	        mockMvc.perform(get("/register"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.view().name("register"))
	                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
	    }

	    @Test
	    void testRegisterUser() throws Exception {
	        doNothing().when(loginService).createUser(any(UserInfo.class));

	        mockMvc.perform(post("/register")
	                        .param("username", "john")
	                        .param("password", "password")
	                        .param("role", "USER"))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));

	        verify(loginService, times(1)).createUser(any(UserInfo.class));
	    }

	    // ---------- DASHBOARD TEST ----------
	    @Test
	    void testShowDashboard() throws Exception {
	        mockMvc.perform(get("/dashboard"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.view().name("dashboard"));
	    }

	    // ---------- ADMIN HOME TEST ----------
	    @Test
	    void testAdminHome() throws Exception {
	        mockMvc.perform(get("/admin/home"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.view().name("admin"));
	    }

	    // ---------- CUSTOMER HOME TEST ----------
	    @Test
	    void testCustomerHome() throws Exception {
	        mockMvc.perform(get("/customer/home"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.view().name("user"));
	    }
}
