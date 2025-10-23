package com.example.realestateproject.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.example.realestateproject.com.example.realestateproject.controller.LoginController;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.repository.UserInfoRepository;
import com.example.realestateproject.repository.UserRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

	 @Mock
	    private UserInfoRepository repository;

	    @Mock
	    private UserRepository userRepository;

	    @Mock
	    private PasswordEncoder passwordEncoder;

	    @Mock
	    private Model model;

	    @InjectMocks
	    private LoginController loginController;

	    private MockMvc mockMvc;

	    @BeforeEach
	    void setUp() {
	        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	    }

	    // ---------------- showLoginForm ----------------
//	    @Test
//	    void testShowLoginForm_ShouldReturnLoginView() throws Exception {
//	        mockMvc.perform(get("/login"))
//	               .andExpect(status().isOk())
//	               .andExpect(view().name("login"));
//	    }

	    // ---------------- showRegisterForm ----------------
	    @Test
	    void testShowRegisterForm_ShouldAddUserAndReturnRegisterView() {
	        String view = loginController.showRegisterForm(model);

	        assertThat(view).isEqualTo("register");
	        verify(model, times(1)).addAttribute(eq("user"), any());
	    }

	    // ---------------- processRegister ----------------
	    @Test
	    void testProcessRegister_ShouldEncodePasswordAndSaveUser() {
	        UserInfo user = new UserInfo();
	        user.setPassword("plainPassword");
	        user.setRoles("CUSTOMER");

	        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

	        String view = loginController.processRegister(user);

	        assertThat(user.getPassword()).isEqualTo("encodedPassword");
	        assertThat(user.getRoles()).isEqualTo("ROLE_CUSTOMER");
	        verify(repository, times(1)).save(user);
	        assertThat(view).isEqualTo("redirect:/login");
	    }

	    // ---------------- showDashboard ----------------
	    @Test
	    void testShowDashboard_ShouldReturnDashboardView() {
	        String view = loginController.showDashboard();
	        assertThat(view).isEqualTo("dashboard");
	    }

	    // ---------------- adminhome ----------------
	    @Test
	    void testAdminhome_ShouldReturnAdminView() {
	        String view = loginController.adminhome();
	        assertThat(view).isEqualTo("admin");
	    }

	    // ---------------- userhome ----------------
	    @Test
	    void testUserhome_ShouldReturnUserView() {
	        String view = loginController.userhome();
	        assertThat(view).isEqualTo("user");
	    }

}
