package com.example.realestateproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
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

import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.repository.PropertyDetailsRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	 @Mock
	    private PropertyDetailsRepository repository;

	    @InjectMocks
	    private CustomerService service;

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

	    // ---------------- saveDetails ----------------
	    @Test
	    void testSaveDetails() {
	        service.saveDetails(property1);

	        verify(repository, times(1)).save(property1);
	    }

	    // ---------------- getProperties ----------------
	    @Test
	    void testGetProperties() {
	        when(repository.findAll()).thenReturn(Arrays.asList(property1, property2));

	        List<PropertyDetails> result = service.getProperties();

	        assertThat(result).hasSize(2).containsExactly(property1, property2);
	        verify(repository, times(1)).findAll();
	    }

	    // ---------------- editProperty ----------------
	    @Test
	    void testEditProperty_Success() {
	        PropertyDetails updated = new PropertyDetails();
	        updated.setLocation("San Francisco");

	        when(repository.findById(1L)).thenReturn(Optional.of(property1));
	        when(repository.save(updated)).thenReturn(updated);

	        PropertyDetails result = service.editProperty(1L, updated);

	        assertThat(result).isNotNull();
	        assertThat(result.getLocation()).isEqualTo("San Francisco");
	        verify(repository, times(1)).findById(1L);
	        verify(repository, times(1)).save(updated);
	    }

	    @Test
	    void testEditProperty_NotFound() {
	        PropertyDetails updated = new PropertyDetails();
	        updated.setLocation("San Francisco");

	        when(repository.findById(5L)).thenReturn(Optional.empty());

	        PropertyDetails result = service.editProperty(5L, updated);

	        assertThat(result).isNull();
	        verify(repository, times(1)).findById(5L);
//	        verify(repository, never()).save(updated);
	    }

	    // ---------------- deleteProperty ----------------
	    @Test
	    void testDeleteProperty() {
	        service.deleteProperty(1L);

	        verify(repository, times(1)).deleteById(1L);
	    }

}
