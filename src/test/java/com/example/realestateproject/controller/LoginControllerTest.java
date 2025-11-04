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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.example.realestateproject.com.example.realestateproject.config.SecurityConfig;
import com.example.realestateproject.com.example.realestateproject.controller.LoginController;
import com.example.realestateproject.com.example.realestateproject.controller.UserController;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.enums.UserRole;
import com.example.realestateproject.repository.UserInfoRepository;
import com.example.realestateproject.repository.UserRepository;
import com.example.realestateproject.service.LoginService;
import com.example.realestateproject.service.PropertyDetailsService;
import com.example.realestateproject.service.UserInfoUserDetailsService;
import com.example.realestateproject.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.bson.types.ObjectId;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = LoginController.class)
@Import(SecurityConfig.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @MockBean
    private PropertyDetailsService propertyDetailsService; 
    
    @MockBean
    private UserInfoRepository userInfoRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserInfo user;
    
    @MockBean
    private UserInfoUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
    	userInfoRepository.deleteAll();
        user = new UserInfo();
        user.setId(new ObjectId("69070d18c85849279ad4643e")); // assuming your _id field is String
        user.setUsername("john");
        user.setPassword("password");
        user.setRole(UserRole.CUSTOMER);
    }

    @Test
    void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("login"));
               
    }

    @Test
    void testShowRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
               .andExpect(status().isOk())
               .andExpect(view().name("register"));
              
    }

    @Test
    void testRegisterUser() throws Exception {
        doNothing().when(loginService).createUser(any(UserInfo.class));

        mockMvc.perform(post("/register")
                        .flashAttr("user", user)
                        .with(csrf()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/login"));

        verify(loginService, times(1)).createUser(any(UserInfo.class));
    }

    @Test
    @WithMockUser(username="cust", roles={"CUSTOMER"})
    void testShowDashboard() throws Exception {
        mockMvc.perform(get("/userdashboard"))
               .andExpect(status().isOk())
               .andExpect(view().name("userdashboard"));
    }

    
}