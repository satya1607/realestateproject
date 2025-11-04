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
    private PropertyDetailsRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        PropertyDetails p1 = new PropertyDetails();
        p1.setTitle("Cozy Apartment");
        p1.setDescription("Nice city view");
        p1.setLocation("New York");
        p1.setPrice(1000);
        p1.setType(Type.SALE);
        repository.save(p1);

        PropertyDetails p2 = new PropertyDetails();
        p2.setTitle("Luxury Villa");
        p2.setDescription("Sea side");
        p2.setLocation("Miami");
        p2.setPrice(2000);
        p2.setType(Type.RENT);
        repository.save(p2);

        PropertyDetails p3 = new PropertyDetails();
        p3.setTitle("Budget Flat");
        p3.setDescription("Nearby station");
        p3.setLocation("New York Suburb");
        p3.setPrice(500);
        p3.setType(Type.SALE);
        repository.save(p3);
    }

    @Test
    void whenKeywordMatchesLocation_thenReturnsMatchingRecords() {
        List<PropertyDetails> results = repository.findByKeywordRegexOrPrice("York", null);
        assertThat(results).isNotEmpty();
        assertThat(results).allMatch(p -> p.getLocation().toLowerCase().contains("york"));
    }

    @Test
    void whenKeywordMatchesType_thenReturnsMatchingRecords() {
        // Suppose type is string form, replace accordingly
        List<PropertyDetails> results = repository.findByKeywordRegexOrPrice("SALE", null);
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getTitle()).isEqualTo("Cozy Apartment");
    }

    @Test
    void whenPriceMatchesExactly_thenReturnsMatchingRecord() {
        List<PropertyDetails> results = repository.findByKeywordRegexOrPrice("nomatch", 1000);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getPrice()).isEqualTo(1000);
    }

    @Test
    void whenNoMatch_thenReturnsEmpty() {
        List<PropertyDetails> results = repository.findByKeywordRegexOrPrice("UnknownCity", 9999);
        assertThat(results).isEmpty();
    }
}
