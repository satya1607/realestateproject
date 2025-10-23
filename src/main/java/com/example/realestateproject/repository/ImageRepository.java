package com.example.realestateproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.realestateproject.entity.Image;

@Repository
public interface ImageRepository extends MongoRepository<Image,String>{
      
}
