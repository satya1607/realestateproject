package com.example.realestateproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.repository.PropertyDetailsRepository;

@Service
public class PropertyDetailsService {

	@Autowired
	 private PropertyDetailsRepository repository;
	 
	 public List<PropertyDetails> getAllProperties(){
	  List<PropertyDetails> list =  (List<PropertyDetails>)repository.findAll();
	  return list;
	 }
	 
	 /*
	  * TODO: Get Properties By keyword
	  */
	 public List<PropertyDetails> getByKeyword(String keyword){
	  return repository.findByKeyword(keyword);
	 }
	 
	 public void save(PropertyDetails propertyDetails, MultipartFile file) {
		    try {
		      propertyDetails.setImageData(file.getBytes());
		      repository.save(propertyDetails);
		    } catch (Exception e) {
		      System.out.println("Some internal error occurred");
		    }
      }
	 public Optional<PropertyDetails> findById(Long imageId) {
		    return repository.findById(imageId);
		  }
}
