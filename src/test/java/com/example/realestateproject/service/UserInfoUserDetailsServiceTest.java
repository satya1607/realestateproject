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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.repository.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
class UserInfoUserDetailsServiceTest {

	@Mock
    private UserInfoRepository repository;

    @InjectMocks
    private UserInfoUserDetailsService userDetailsService;

    @Test
    void loadUserByUsername_UserExists_ShouldReturnUserDetails() {
        // given
        UserInfo user = new UserInfo();
        user.setUsername("john");
        user.setPassword("secret");
        user.setRoles("ROLE_ADMIN");

        when(repository.findByUsername("john")).thenReturn(Optional.of(user));

        // when
        UserDetails userDetails = userDetailsService.loadUserByUsername("john");

        // then
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("john");
        assertThat(userDetails.getPassword()).isEqualTo("secret");
        assertThat(userDetails.getAuthorities()).extracting("authority").containsExactly("ROLE_ADMIN");
        verify(repository, times(1)).findByUsername("john");
    }

    @Test
    void loadUserByUsername_UserNotFound_ShouldThrowException() {
        // given
        when(repository.findByUsername("unknown")).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("unknown"))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessageContaining("User not found in database.");

        verify(repository, times(1)).findByUsername("unknown");
    }
}
