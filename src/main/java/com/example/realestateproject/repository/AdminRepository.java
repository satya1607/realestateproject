package com.example.realestateproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.realestateproject.entity.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin,String>{
	public Admin findByUsername(String username);
}
