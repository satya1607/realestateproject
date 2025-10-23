package com.example.realestateproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.realestateproject.entity.Image;
import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.repository.PropertyDetailsRepository;

@Service
public class CustomerService {
   
	@Autowired
	private PropertyDetailsRepository propertyDetailsRepository;
	
	public void saveDetails(PropertyDetails propertyDetails) {
		propertyDetailsRepository.save(propertyDetails);
	}
    
	public List<PropertyDetails> getProperties(){
		return propertyDetailsRepository.findAll();
	}
	public PropertyDetails editProperty(Long id,PropertyDetails propertyDetails) {
		Optional<PropertyDetails> existingProperty=propertyDetailsRepository.findById(id);
		if(existingProperty==null) {
			return null;
		}
		propertyDetails.setId(id);
		return propertyDetailsRepository.save(propertyDetails);	
	}
	
	public void deleteProperty(Long id) {
		 propertyDetailsRepository.deleteById(id);
	}
	
}
