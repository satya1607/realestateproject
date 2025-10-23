package com.example.realestateproject.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.enums.Type;

@DataMongoTest
class PropertyDetailsRepositoryTest {

	 @Autowired
	    private PropertyDetailsRepository propertyDetailsRepository;

	    @Test
	    void testFindByKeyword_ShouldMatchLocation() {
	        // given
	        PropertyDetails property1 = new PropertyDetails();
	        property1.setLocation("Hyderabad");
	        property1.setType(Type.RENT);
	        property1.setPrice(5000000);
	        propertyDetailsRepository.save(property1);

	        PropertyDetails property2 = new PropertyDetails();
	        property2.setLocation("Bangalore");
	        property2.setType(Type.SALE);
	        property2.setPrice(8000000);
	        propertyDetailsRepository.save(property2);

	        // when
	        List<PropertyDetails> results = propertyDetailsRepository.findByKeyword("WHITEFIELD");

	        // then
	        assertThat(results).isNotEmpty();
	        assertThat(results.get(0).getLocation()).isEqualTo("WHITEFIELD");
	    }

	    @Test
	    void testFindByKeyword_ShouldMatchType() {
	        PropertyDetails property = new PropertyDetails();
	        property.setLocation("Chennai");
	        property.setType(Type.RENT);
	        property.setPrice(6000000);
	        propertyDetailsRepository.save(property);

	        List<PropertyDetails> results = propertyDetailsRepository.findByKeyword("House");

	        assertThat(results).isNotEmpty();
	        assertThat(results.get(0).getType()).isEqualTo(Type.RENT);
	    }

	    @Test
	    void testFindByKeyword_ShouldMatchPrice() {
	        PropertyDetails property = new PropertyDetails();
	        property.setLocation("Delhi");
	        property.setType(Type.RENT);
	        property.setPrice(7000000);
	        propertyDetailsRepository.save(property);

	        List<PropertyDetails> results = propertyDetailsRepository.findByKeyword("7000");

	        assertThat(results).isNotEmpty();
	        assertThat(results.get(0).getPrice()).isEqualTo(40000);
	    }

	    @Test
	    void testFindByKeyword_NoMatch() {
	        List<PropertyDetails> results = propertyDetailsRepository.findByKeyword("NonExisting");
	        assertThat(results).isNotEmpty();
	    }

}
