package com.example.realestateproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.repository.PropertyDetailsRepository;

@ExtendWith(MockitoExtension.class)
class PropertyDetailsServiceTest {

    @Mock
    private PropertyDetailsRepository repository;

    @InjectMocks
    private PropertyDetailsService service;

    private PropertyDetails prop1;
    private PropertyDetails prop2;

    @BeforeEach
    void setUp() {
        prop1 = new PropertyDetails();
        prop1.setId("1");
        prop1.setTitle("Title1");
        prop1.setDescription("Desc1");
        prop1.setLocation("Loc1");
        prop1.setPrice(100);
       
        prop2 = new PropertyDetails();
        prop2.setId("2");
        prop2.setTitle("Title2");
        prop2.setDescription("Desc2");
        prop2.setLocation("Loc2");
        prop2.setPrice(200);
    }

    @Test
    void getAllProperties_ShouldReturnListFromRepository() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(prop1, prop2));

        // Act
        List<PropertyDetails> result = service.getAllProperties();

        // Assert
        assertThat(result).hasSize(2)
                          .contains(prop1, prop2);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getByKeyword_WhenMatches_ShouldReturnMatchingList() {
        // Arrange
        String keyword = "Loc1";
        Integer price = null;
        when(repository.findByKeywordRegexOrPrice(keyword, price))
            .thenReturn(List.of(prop1));

        // Act
        List<PropertyDetails> result = service.getByKeyword(keyword, price);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLocation()).isEqualTo("Loc1");
        verify(repository).findByKeywordRegexOrPrice(keyword, price);
    }

    @Test
    void getByKeyword_WhenPriceMatches_ShouldReturnMatchingList() {
        // Arrange
        String keyword = "";
        Integer price = 200;
        when(repository.findByKeywordRegexOrPrice(keyword, price))
            .thenReturn(List.of(prop2));

        // Act
        List<PropertyDetails> result = service.getByKeyword(keyword, price);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPrice()).isEqualTo(200);
        verify(repository).findByKeywordRegexOrPrice(keyword, price);
    }

    @Test
    void findById_WhenExists_ShouldReturnOptionalWithValue() {
        
        String id = "someId";
        when(repository.findById(id)).thenReturn(Optional.of(prop1));

        Optional<PropertyDetails> result = service.findById(id);

        assertThat(result).isPresent()
                          .contains(prop1);
        verify(repository).findById(id);
    }

    @Test
    void findById_WhenDoesNotExist_ShouldReturnEmptyOptional() {
        String id = "missingId";
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<PropertyDetails> result = service.findById(id);
        assertThat(result).isEmpty();
        verify(repository).findById(id);
    }
    @Test
    void save_WhenFileBytesAvailable_ShouldSetImageDataAndSave() throws Exception {

        MultipartFile file = mock(MultipartFile.class);
        byte[] data = {1, 2, 3};
        when(file.getBytes()).thenReturn(data);
        PropertyDetails p = new PropertyDetails();
        p.setId("3");

        service.save(p, file);

        // We cannot easily inspect private state via repository, but we inspect the argument passed to repository.save(...)
        ArgumentCaptor<PropertyDetails> captor = ArgumentCaptor.forClass(PropertyDetails.class);
        verify(repository).save(captor.capture());
        PropertyDetails savedArg = captor.getValue();
        assertThat(savedArg.getImageData()).isEqualTo(data);
    }

    @Test
    void save_WhenFileThrowsException_ShouldHandleAndNotThrow() throws Exception {
        
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenThrow(new RuntimeException("bytes error"));
        PropertyDetails p = new PropertyDetails();
        p.setId("4");

        service.save(p, file);
        verify(repository, never()).save(any());
    }
}