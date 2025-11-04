package com.example.realestateproject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.realestateproject.com.example.realestateproject.controller.CustomerController;
import com.example.realestateproject.entity.Image;
import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.repository.ImageRepository;
import com.example.realestateproject.service.CustomerService;
import com.example.realestateproject.service.ImageService;
import com.example.realestateproject.service.PropertyDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private PropertyDetailsService propertyDetailsService;

    @MockBean
    private ImageService imageService;

    @MockBean
    private ImageRepository imageRepository;

    private PropertyDetails property;

    @BeforeEach
    void setUp() {
        property = new PropertyDetails();
        property.setId("1");
        property.setTitle("Beautiful House");
        property.setLocation("Hyderabad");
        property.setPrice(100000);
        property.setDescription("Spacious and well-lit");
    }

    @Test
    @WithMockUser(username="user1", roles={"CUSTOMER"})
    void testHomeWithoutKeyword() throws Exception {
        List<PropertyDetails> properties = List.of(property);
        Mockito.when(propertyDetailsService.getAllProperties()).thenReturn(properties);
        Mockito.when(imageService.getAllImages()).thenReturn(List.of());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attributeExists("imageMap"));
    }
    @WithMockUser(roles={"CUSTOMER"})
    @Test
    void testHomeWithKeyword() throws Exception {
        Mockito.when(propertyDetailsService.getByKeyword(eq("Hyderabad"), any())).thenReturn(List.of(property));

        mockMvc.perform(get("/search").param("keyword", "Hyderabad"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("list"));
    }

    @Test
    @WithMockUser(username="custUser", roles={"CUSTOMER"})
    void testCreateProperty() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "fake-image".getBytes()
        );

        Image mockImage = new Image();
        mockImage.setId("img123");
        Mockito.when(imageService.save(any(Image.class), any())).thenReturn(mockImage);

        mockMvc.perform(multipart("/customer/create")
                        .file(mockFile)
                        .with(csrf())
                        .param("title", "House")
                        .param("location", "Hyderabad")
                        .param("price", "120000")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/search"));
    }

    @Test
    @WithMockUser(roles = {"CUSTOMER"})
    void testShowAddPropertyForm() throws Exception {
        mockMvc.perform(get("/customer/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerpost"))
                .andExpect(model().attributeExists("propertyDetails"));
    }
    @WithMockUser(username="user1", roles={"CUSTOMER"})
    @Test
    void testShowEditForm() throws Exception {
        Mockito.when(customerService.getPropertyById("1")).thenReturn(property);

        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editpropertyform"))
                .andExpect(model().attributeExists("property"));
    }

    @Test
    @WithMockUser(username="someUser", roles={"CUSTOMER"})
    void testEditProperty() throws Exception {
        mockMvc.perform(put("/customer/edit/1")
        		 .with(csrf())
                        .param("title", "Updated Title")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/userdashboard"));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void testDeletePropertyForCustomer() throws Exception {
        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/userdashboard"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testDeletePropertyForAdmin() throws Exception {
        mockMvc.perform(get("/admin/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admindashboard"));
    }
}