package com.example.realestateproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
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
    private CustomerService customerService;

    private PropertyDetails prop1;
    private PropertyDetails prop2;

    @BeforeEach
    void setUp() {
        prop1 = new PropertyDetails();
        prop1.setId("1");
        prop1.setTitle("Test Property 1");
        prop1.setDescription("Desc1");
        prop1.setPrice(500);
        prop1.setLocation("Location1");

        prop2 = new PropertyDetails();
        prop2.setId("2");
        prop2.setTitle("Test Property 2");
        prop2.setDescription("Desc2");
        prop2.setPrice(1000);
        prop2.setLocation("Location2");
    }

    @Test
    void saveDetails_ShouldCallRepositorySave() {
        // Arrange
        when(repository.save(prop1)).thenReturn(prop1);

        // Act
        customerService.saveDetails(prop1);

        // Assert
        verify(repository, times(1)).save(prop1);
    }

    @Test
    void getProperties_ShouldReturnAllProperties() {
        // Arrange
        List<PropertyDetails> list = Arrays.asList(prop1, prop2);
        when(repository.findAll()).thenReturn(list);

        // Act
        List<PropertyDetails> result = customerService.getProperties();

        // Assert
        assertThat(result).hasSize(2)
                          .contains(prop1, prop2);
        verify(repository, times(1)).findAll();
    }

    @Test
    void editProperty_WhenExistingId_ShouldUpdateAndSave() {
        // Arrange
        String id = prop1.getId();
        when(repository.findById(id)).thenReturn(Optional.of(prop1));
        PropertyDetails update = new PropertyDetails();
        update.setTitle("Updated Title");
        update.setDescription("Updated Desc");
        update.setPrice(750);
        update.setLocation("NewLocation");

        when(repository.save(any(PropertyDetails.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PropertyDetails saved = customerService.editProperty(id, update);

        // Assert
        assertThat(saved.getTitle()).isEqualTo("Updated Title");
        assertThat(saved.getLocation()).isEqualTo("NewLocation");
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(update);
    }

    @Test
    void editProperty_WhenNonExistingId_ShouldReturnNull() {
        // Arrange
        String id = "nonExistId";
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act
        PropertyDetails result = customerService.editProperty(id, prop2);

        // Assert
        assertThat(result).isNull();
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any(PropertyDetails.class));
    }

    @Test
    void getPropertyById_WhenFound_ShouldReturnProperty() throws Exception {
        // Arrange
        String id = prop2.getId();
        when(repository.findById(id)).thenReturn(Optional.of(prop2));

        // Act
        PropertyDetails result = customerService.getPropertyById(id);

        // Assert
        assertThat(result).isEqualTo(prop2);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void getPropertyById_WhenNotFound_ShouldThrowException() {
        // Arrange
        String id = "missingId";
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class,
                     () -> customerService.getPropertyById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void deleteProperty_ShouldCallRepositoryDeleteById() {
        // Arrange
        String id = prop1.getId();

        // Act
        customerService.deleteProperty(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }
}