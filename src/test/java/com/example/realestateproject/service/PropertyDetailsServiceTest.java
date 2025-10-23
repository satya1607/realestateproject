package com.example.realestateproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.repository.PropertyDetailsRepository;

@ExtendWith(MockitoExtension.class)
class PropertyDetailsServiceTest {

	 @Mock
	    private PropertyDetailsRepository repository;

	    @InjectMocks
	    private PropertyDetailsService service;

	    private PropertyDetails property1;
	    private PropertyDetails property2;

	    @BeforeEach
	    void setUp() {
	        property1 = new PropertyDetails();
	        property1.setId(1L);
	        property1.setLocation("New York");

	        property2 = new PropertyDetails();
	        property2.setId(2L);
	        property2.setLocation("London");
	    }

	    // ---------------- getAllProperties ----------------
	    @Test
	    void testGetAllProperties() {
	        when(repository.findAll()).thenReturn(Arrays.asList(property1, property2));

	        List<PropertyDetails> result = service.getAllProperties();

	        assertThat(result).hasSize(2);
	        assertThat(result).containsExactly(property1, property2);
	        verify(repository, times(1)).findAll();
	    }

	    // ---------------- getByKeyword ----------------
	    @Test
	    void testGetByKeyword() {
	        String keyword = "New";
	        when(repository.findByKeyword(keyword)).thenReturn(Arrays.asList(property1));

	        List<PropertyDetails> result = service.getByKeyword(keyword);

	        assertThat(result).hasSize(1);
	        assertThat(result.get(0).getLocation()).isEqualTo("New York");
	        verify(repository, times(1)).findByKeyword(keyword);
	    }

	    // ---------------- save ----------------
	    @Test
	    void testSavePropertyWithFile() throws Exception {
	        MockMultipartFile file = new MockMultipartFile("image", "test.png", "image/png", "dummy data".getBytes());

	        service.save(property1, file);

	        assertThat(property1.getImageData()).isNotNull();
	        verify(repository, times(1)).save(property1);
	    }

	    // ---------------- findById ----------------
	    @Test
	    void testFindById_Found() {
	        when(repository.findById(1L)).thenReturn(Optional.of(property1));

	        Optional<PropertyDetails> result = service.findById(1L);

	        assertThat(result).isPresent();
	        assertThat(result.get().getLocation()).isEqualTo("New York");
	        verify(repository, times(1)).findById(1L);
	    }

	    @Test
	    void testFindById_NotFound() {
	        when(repository.findById(3L)).thenReturn(Optional.empty());

	        Optional<PropertyDetails> result = service.findById(3L);

	        assertThat(result).isEmpty();
	        verify(repository, times(1)).findById(3L);
	    }
	

}
