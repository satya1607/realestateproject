package com.example.realestateproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.realestateproject.entity.Register;
import com.example.realestateproject.repository.RegisterRepository;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

	 @Mock
	    private RegisterRepository registerRepository;

	    @InjectMocks
	    private LoginService loginService;

	    private Register register;

	    private BCryptPasswordEncoder encoder;

	    @BeforeEach
	    void setUp() {
	        register = new Register();
	        register.setEmail("john@gmail.com");
	        register.setPassword("plainPassword");

	        encoder = new BCryptPasswordEncoder();
	    }

	    @Test
	    void testSave_ShouldEncodePasswordAndSave() {
	        // Call the service method
	        loginService.save(register);

	        // The password should be encoded
	        assertThat(register.getPassword()).isNotEqualTo("plainPassword");
	        assertThat(encoder.matches("plainPassword", register.getPassword())).isTrue();

	        // Repository save should be called
	        verify(registerRepository, times(1)).save(register);
	    }
}
