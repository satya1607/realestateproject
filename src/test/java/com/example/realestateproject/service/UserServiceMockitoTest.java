package com.example.realestateproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.realestateproject.entity.User;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.enums.UserRole;
import com.example.realestateproject.repository.UserInfoRepository;
import com.example.realestateproject.repository.UserRepository;

import com.example.realestateproject.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceMockitoTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private UserService userService;

    private UserInfo user1;
    private ObjectId id1;

    @BeforeEach
    void setUp() {
        id1 = new ObjectId();
        user1 = new UserInfo();
        // Assuming you added a setter for id or _id. Adjust accordingly.
        user1.setId(id1);            // or set _id field if needed
        user1.setUsername("john.doe@example.com");
        user1.setPassword("encryptedPass");
        user1.setRole(UserRole.CUSTOMER);
        user1.setName("John Doe");
    }

    @Test
    void saveUser_ShouldCallRepositorySave() {
        // Arrange
        when(userInfoRepository.save(user1)).thenReturn(user1);

        // Act
        userService.saveUser(user1);

        // Assert
        verify(userInfoRepository, times(1)).save(user1);
    }

    @Test
    void displayUsers_ShouldReturnAllUsers() {
        // Arrange
        UserInfo user2 = new UserInfo();
        ObjectId id2 = new ObjectId();
        user2.setId(id2);
        user2.setUsername("jane.doe@example.com");
        user2.setPassword("pass2");
        user2.setRole(UserRole.ADMIN);
        user2.setName("Jane Doe");

        List<UserInfo> allUsers = List.of(user1, user2);
        when(userInfoRepository.findAll()).thenReturn(allUsers);

        // Act
        List<UserInfo> result = userService.displayUsers();

        // Assert
        assertThat(result).hasSize(2).contains(user1, user2);
        verify(userInfoRepository, times(1)).findAll();
    }

    @Test
    void getUserById_WhenFound_ShouldReturnUser() {
        // Arrange
        when(userInfoRepository.findById(id1)).thenReturn(Optional.of(user1));

        // Act
        UserInfo result = userService.getUserById(id1);

        // Assert
        assertThat(result).isEqualTo(user1);
        verify(userInfoRepository, times(1)).findById(id1);
    }

    @Test
    void getUserById_WhenNotFound_ShouldThrowException() {
        // Arrange
        when(userInfoRepository.findById(id1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUserById(id1));
        verify(userInfoRepository, times(1)).findById(id1);
    }

    @Test
    void deleteUser_ShouldCallRepositoryDeleteById() {
        // Act
        userService.deleteUser(id1);

        // Assert
        verify(userInfoRepository, times(1)).deleteById(id1);
    }

    @Test
    void editUser_WhenUserExists_ShouldUpdateAndSave() {
        // Arrange
        when(userInfoRepository.findById(id1)).thenReturn(Optional.of(user1));

        UserInfo update = new UserInfo();
        update.setUsername("updated@example.com");
        update.setPassword("newPass");
        update.setRole(UserRole.CUSTOMER);
        update.setName("Updated Name");

        when(userInfoRepository.save(any(UserInfo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserInfo result = userService.editUser(id1, update);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("updated@example.com");
        assertThat(result.getName()).isEqualTo("Updated Name");
        verify(userInfoRepository, times(1)).findById(id1);
        verify(userInfoRepository, times(1)).save(update);
    }

    @Test
    void editUser_WhenUserDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(userInfoRepository.findById(id1)).thenReturn(Optional.empty());

        UserInfo update = new UserInfo();
        update.setUsername("will.not.save@example.com");

        // Act
        UserInfo result = userService.editUser(id1, update);

        // Assert
        assertThat(result).isNull();
        verify(userInfoRepository, times(1)).findById(id1);
        verify(userInfoRepository, never()).save(any(UserInfo.class));
    }
}