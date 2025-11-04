package com.example.realestateproject.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.realestateproject.entity.PropertyDetails;

@Repository
public interface PropertyDetailsRepository extends MongoRepository<PropertyDetails,String> {

	 @Query("{ '$or': [ " +
	           "  { 'location': { $regex: ?0, $options: 'i' } }, " +
	           "  { 'type':     { $regex: ?0, $options: 'i' } }, " +
	           "  { 'price':    { $eq:   ?1 } } " +
	           "] }")
	    List<PropertyDetails> findByKeywordRegexOrPrice(String keyword, Integer price);

}
