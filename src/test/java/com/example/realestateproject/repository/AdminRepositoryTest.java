package com.example.realestateproject.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.realestateproject.entity.Admin;

@DataMongoTest
class AdminRepositoryTest {

	 @Autowired
	    private AdminRepository adminRepository;

	 @BeforeEach
	    void setUp() {
	        adminRepository.deleteAll();
	 }
	    @Test
	    void testFindByUsername() {
	        Admin admin = new Admin();
	        admin.setUsername("adminUser");
	        admin.setPassword("secret");

	        adminRepository.save(admin);

	        Admin found = adminRepository.findByUsername("adminUser");

	        assertThat(found).isNotNull();
	        assertThat(found.getUsername()).isEqualTo("adminUser");
	    }
}
