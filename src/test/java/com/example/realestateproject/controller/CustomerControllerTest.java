package com.example.realestateproject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.realestateproject.com.example.realestateproject.controller.CustomerController;
import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.repository.ImageRepository;
import com.example.realestateproject.service.CustomerService;
import com.example.realestateproject.service.ImageService;
import com.example.realestateproject.service.PropertyDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

	 @Mock
	    private CustomerService customerService;

	    @Mock
	    private PropertyDetailsService propertyService;

	    @Mock
	    private ImageService imageService;

	    @Mock
	    private ImageRepository imageRepository;

	    @InjectMocks
	    private CustomerController controller;

	    private MockMvc mockMvc;

	    @BeforeEach
	    void setUp() {
	        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	    }

	    // ---------------- GET / or /search ----------------
	    @Test
	    void testHome_WithKeyword_ShouldReturnIndexView() throws Exception {
	        when(propertyService.getByKeyword("test"))
	            .thenReturn(Arrays.asList(new PropertyDetails()));

	        mockMvc.perform(get("/search").param("keyword", "test"))
	               .andExpect(status().isOk())
	               .andExpect(view().name("index"))
	               .andExpect(model().attributeExists("list"));

	        verify(propertyService, times(1)).getByKeyword("test");
	    }

	    @Test
	    void testHome_WithoutKeyword_ShouldReturnAllProperties() throws Exception {
	        when(propertyService.getAllProperties())
	            .thenReturn(Arrays.asList(new PropertyDetails()));

	        mockMvc.perform(get("/"))
	               .andExpect(status().isOk())
	               .andExpect(view().name("index"))
	               .andExpect(model().attributeExists("list"));

	        verify(propertyService, times(1)).getAllProperties();
	    }

	    // ---------------- GET /image/{imageId} ----------------
	    @Test
	    void testDownloadImage() throws Exception {
	        byte[] imageData = "dummy".getBytes();
	        PropertyDetails property = new PropertyDetails();
	        property.setImageData(imageData);

	        when(propertyService.findById(1L)).thenReturn(Optional.of(property));

	        mockMvc.perform(get("/image/1"))
	               .andExpect(status().isOk());
//	               .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE));

	        verify(propertyService, times(1)).findById(1L);
	    }

	    // ---------------- POST /propertyDetails/add ----------------
	    @Test
	    void testHandleProfileAdd() throws Exception {
	        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE.toString(), "dummy".getBytes());

	        mockMvc.perform(multipart("/propertyDetails/add")
	                .file(file)
	                .param("location", "NY"))
	               .andExpect(status().is3xxRedirection())
	               .andExpect(redirectedUrl("/index"));

	        verify(propertyService, times(1)).save(any(PropertyDetails.class), eq(file));
	    }

	    // ---------------- GET /customer/create ----------------
	    @Test
	    void testShowAddPropertyForm() throws Exception {
	        mockMvc.perform(get("/customer/create"))
	               .andExpect(status().isOk())
	               .andExpect(view().name("customerpost"))
	               .andExpect(model().attributeExists("propertyDetails"));
	    }

	    // ---------------- POST /customer/create ----------------
	    @Test
	    void testCreateProperty() throws Exception {
	        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE.toString(), "dummy".getBytes());

	        mockMvc.perform(multipart("/customer/create")
	                .file(file)
	                .param("location", "NY"))
	               .andExpect(status().is3xxRedirection())
	               .andExpect(redirectedUrl("/"));

	        verify(imageService, times(1)).save(any(), eq(file));
	        verify(customerService, times(1)).saveDetails(any(PropertyDetails.class));
	    }

	    // ---------------- GET /customer/display ----------------
	    @Test
	    void testDisplayProperties() throws Exception {
	        when(customerService.getProperties()).thenReturn(Arrays.asList(new PropertyDetails()));

	        mockMvc.perform(get("/customer/display"))
	               .andExpect(status().isOk());

	        verify(customerService, times(1)).getProperties();
	    }

	    // ---------------- PUT /customer/edit/{id} ----------------
	    @Test
	    void testEditProperty() throws Exception {
	        PropertyDetails property = new PropertyDetails();
	        property.setId(1L);

	        when(customerService.editProperty(eq(1L), any(PropertyDetails.class))).thenReturn(property);

	        mockMvc.perform(put("/customer/edit/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content("{\"location\":\"NY\"}"))
	               .andExpect(status().isOk());

	        verify(customerService, times(1)).editProperty(eq(1L), any(PropertyDetails.class));
	    }

	    // ---------------- DELETE /customer/delete/{id} ----------------
	    @Test
	    void testDeleteProperty() throws Exception {
	        mockMvc.perform(delete("/customer/delete/1"))
	               .andExpect(status().isOk());

	        verify(customerService, times(1)).deleteProperty(1L);
	    }

}
