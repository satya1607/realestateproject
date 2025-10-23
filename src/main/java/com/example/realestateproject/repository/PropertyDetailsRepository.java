package com.example.realestateproject.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.realestateproject.entity.PropertyDetails;

@Repository
public interface PropertyDetailsRepository extends MongoRepository<PropertyDetails,Long> {

	@Query(value = "select * from PropertyDetails p where p.location like %:keyword% or p.type like %:keyword%"
			+ "or p.price like %:keyword% ")
	 List<PropertyDetails> findByKeyword(@Param("keyword") String keyword);
	
//	PropertyDetails findById(int id);
//	
//	PropertyDetails deleteById(int id);
}
