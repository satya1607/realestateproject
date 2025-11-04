package com.example.realestateproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.realestateproject.entity.Register;
import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.exception.UserNotFoundException;
import com.example.realestateproject.repository.RegisterRepository;
import com.example.realestateproject.repository.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserInfoRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @Test
    void createUser_ShouldEncodePasswordAndSaveUser() {
        // given
        UserInfo user = new UserInfo();
        user.setUsername("john");
        user.setPassword("rawPassword");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        // when
        loginService.createUser(user);

        // then
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void login_UserExists_ShouldReturnUser() {
        // given
        UserInfo user = new UserInfo();
        user.setUsername("john");
        user.setPassword("secret");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        // when
        UserInfo result = loginService.login("john");

        // then
        assertNotNull(result);
        assertEquals("john", result.getUsername());
        assertEquals("secret", result.getPassword());
        verify(userRepository, times(1)).findByUsername("john");
    }

    @Test
    void login_UserNotFound_ShouldThrowException() {
        // given
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> loginService.login("unknown"))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessageContaining("User not found in database");

        verify(userRepository, times(1)).findByUsername("unknown");
    }
}