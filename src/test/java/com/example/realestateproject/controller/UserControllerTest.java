package com.example.realestateproject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.realestateproject.com.example.realestateproject.controller.UserController;
import com.example.realestateproject.entity.User;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.enums.UserRole;
import com.example.realestateproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserInfo adminUser;
    private UserInfo customerUser;

    @BeforeEach
    void setup() {
        adminUser = new UserInfo();
        ObjectId adminId = new ObjectId();
        adminUser.setId(adminId);
        adminUser.setUsername("admin@example.com");
        adminUser.setPassword("pass");
        adminUser.setRole(UserRole.ADMIN);
        adminUser.setName("Admin Name");

        customerUser = new UserInfo();
        ObjectId custId = new ObjectId();
        customerUser.setId(custId);
        customerUser.setUsername("cust@example.com");
        customerUser.setPassword("custpass");
        customerUser.setRole(UserRole.CUSTOMER);
        customerUser.setName("Customer Name");
    }

    @Test
    @WithMockUser(username="adminUser", roles={"ADMIN"})
    void displayUsers_ShouldReturnOnlyCustomersInModel() throws Exception {
        // Arrange
        List<UserInfo> allUsers = List.of(adminUser, customerUser);
        when(userService.displayUsers()).thenReturn(allUsers);

        mockMvc.perform(get("/admin/user/display"))
               .andExpect(status().isOk())
               .andExpect(view().name("viewuserdetails"))
               .andExpect(model().attributeExists("onlyCustomers"))
               .andExpect(model().attribute("onlyCustomers", List.of(customerUser)));

        verify(userService, times(1)).displayUsers();
    }
    @Test
    @WithMockUser(username="adminUser", roles={"ADMIN"})
    void showEditForm_WhenValidId_ShouldReturnEditFormWithUser() throws Exception {
        ObjectId id = customerUser.getId();
        when(userService.getUserById(id)).thenReturn(customerUser);

        mockMvc.perform(get("/admin/user/edit/{id}", id.toHexString()))
               .andExpect(status().isOk())
               .andExpect(view().name("edituserform"))
               .andExpect(model().attributeExists("user"))
               .andExpect(model().attribute("user", customerUser));

        verify(userService, times(1)).getUserById(id);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void deleteUser_ShouldRedirectToDisplay() throws Exception {
        // Arrange
        ObjectId id = customerUser.getId();
        doNothing().when(userService).deleteUser(id);

        mockMvc.perform(get("/admin/user/delete/{id}", id.toHexString()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/admin/user/display"));

        verify(userService, times(1)).deleteUser(id);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void editUser_PostShouldRedirect() throws Exception {
       
        ObjectId id = customerUser.getId();
        when(userService.editUser(eq(id), any(UserInfo.class))).thenReturn(customerUser);

        mockMvc.perform(put("/admin/user/edit/{id}", id.toHexString())
                     .with(csrf())       		
                     .param("username", "newcust@example.com")
                     .param("name", "New Customer")
                     .param("password", "newpass")
                     .param("role", "CUSTOMER"))
                     .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/admin/user/display"));

        verify(userService, times(1)).editUser(eq(id), any(UserInfo.class));
    }
}