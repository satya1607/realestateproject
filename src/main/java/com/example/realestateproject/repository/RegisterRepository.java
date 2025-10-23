package com.example.realestateproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.realestateproject.entity.Register;

@Repository
public interface RegisterRepository extends MongoRepository<Register,Integer> {

}
