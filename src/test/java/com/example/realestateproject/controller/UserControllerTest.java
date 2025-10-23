package com.example.realestateproject.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.realestateproject.com.example.realestateproject.controller.UserController;
import com.example.realestateproject.entity.User;
import com.example.realestateproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	 @Mock
	    private UserService userService;

	    @InjectMocks
	    private UserController userController;

	    private MockMvc mockMvc;
	    private ObjectMapper objectMapper;

	    @BeforeEach
	    void setUp() {
	        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	        objectMapper = new ObjectMapper();
	    }

	    // ---------------- POST /admin/user/save ----------------
	    @Test
	    void testSaveUser() throws Exception {
	        User user = new User();
	        user.setId(1);
	        user.setEmail("john@gmail.com");

	        mockMvc.perform(post("/admin/user/save")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(user)))
	               .andExpect(status().isOk());

	        verify(userService, times(1)).saveUser(any(User.class));
	    }

	    // ---------------- GET /admin/user/display ----------------
	    @Test
	    void testDisplayUsers() throws Exception {
	        User user1 = new User();
	        user1.setId(1); user1.setEmail("john@gmail.com");

	        User user2 = new User();
	        user2.setId(2); user2.setEmail("alice@gmail.com");

	        when(userService.displayUsers()).thenReturn(Arrays.asList(user1, user2));

	        mockMvc.perform(get("/admin/user/display"))
	               .andExpect(status().isOk())
	               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	               .andExpect(jsonPath("$[0].id").value(1))
	               .andExpect(jsonPath("$[0].email").value("john@gmail.com"))
	               .andExpect(jsonPath("$[1].id").value(2))
	               .andExpect(jsonPath("$[1].email").value("alice@gmail.com"));

	        verify(userService, times(1)).displayUsers();
	    }

	    // ---------------- PUT /admin/user/edit/{id} ----------------
	    @Test
	    void testEditUser() throws Exception {
	        User updatedUser = new User();
	        updatedUser.setId(1);
	        updatedUser.setEmail("JohnUpdated@gmail.com");

	        when(userService.editUser(eq(1), any(User.class))).thenReturn(updatedUser);

	        mockMvc.perform(put("/admin/user/edit/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(updatedUser)))
	               .andExpect(status().isOk())
	               .andExpect(jsonPath("$.id").value(1))
	               .andExpect(jsonPath("$.email").value("JohnUpdated@gmail.com"));

	        verify(userService, times(1)).editUser(eq(1), any(User.class));
	    }

	    // ---------------- DELETE /admin/user/delete/{id} ----------------
	    @Test
	    void testDeleteUser() throws Exception {
	        mockMvc.perform(delete("/admin/user/delete/1"))
	               .andExpect(status().isOk());

	        verify(userService, times(1)).deleteUser(1);
	    }

}
