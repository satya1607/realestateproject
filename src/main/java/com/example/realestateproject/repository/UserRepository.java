package com.example.realestateproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.realestateproject.entity.Login;
import com.example.realestateproject.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User,Integer> {

	

}
