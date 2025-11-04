package com.example.realestateproject.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.realestateproject.entity.UserInfo;
import com.example.realestateproject.enums.UserRole;

@DataMongoTest
class UserInfoRepositoryTest {

	 @Autowired
	    private UserInfoRepository userInfoRepository;

	 @BeforeEach
	    void setUp() {
	        userInfoRepository.deleteAll();
	    }
	 
	    @Test
	    void testFindByUsername() {
	        // given
	        UserInfo user = new UserInfo();
	        user.setUsername("john_doe");
	        user.setPassword("secret");
	        user.setRole(UserRole.CUSTOMER);
	        userInfoRepository.save(user);

	        // when
	        Optional<UserInfo> result = userInfoRepository.findByUsername("john_doe");

	        // then
	      
	        assertEquals("john_doe", result.get().getUsername());
	        assertThat(result.isPresent());
	
	    }
}
