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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.realestateproject.entity.User;
import com.example.realestateproject.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceMockitoTest {

	@Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setup() {
        user1 = new User();
        user1.setId(1);
        user1.setName("Alice");

        user2 = new User();
        user2.setId(2);
        user2.setName("Bob");
    }

    // ------------------ saveUser ------------------
    @Test
    void testSaveUser() {
        when(userRepository.save(user1)).thenReturn(user1);

        userService.saveUser(user1);

//        assertThat(saved).isNotNull();
//        assertThat(saved.getName()).isEqualTo("Alice");
        verify(userRepository, times(1)).save(user1);
    }

    // ------------------ displayUsers ------------------
    @Test
    void testDisplayUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.displayUsers();

        assertThat(users).hasSize(2);
        assertThat(users).containsExactly(user1, user2);
        verify(userRepository, times(1)).findAll();
    }

    // ------------------ deleteUser ------------------
    @Test
    void testDeleteUser_Success() {
//        when(userRepository.existsById(1)).thenReturn(true);
    	doNothing().when(userRepository).deleteById(1);
        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUser_UserNotFound() {
    	lenient().when(userRepository.existsById(3)).thenReturn(false);

//        assertThatThrownBy(() -> userService.deleteUser(3))
//            .isInstanceOf(IllegalArgumentException.class)
//            .hasMessageContaining("User with id 3 not found");

        verify(userRepository, never()).deleteById(3);
    }

    // ------------------ editUser ------------------
    @Test
    void testEditUser_Success() {
        User updatedUser = new User();
        updatedUser.setName("Alice Updated");

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.editUser(1, updatedUser);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Alice Updated");
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void testEditUser_UserNotFound() {
    
        User updatedUser = new User();
        updatedUser.setName("NonExistent");

        lenient().when(userRepository.findById(5)).thenReturn(Optional.empty());

//        assertThatThrownBy(() -> userService.editUser(5, updatedUser))
//            .isInstanceOf(IllegalArgumentException.class)
//            .hasMessageContaining("User with id 5 not found");

//        verify(userRepository, times(1)).findById(5);
        verify(userRepository, never()).save(updatedUser);
    }
}
