package com.example.realestateproject.entity;

import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.realestateproject.enums.Type;

@Document(collection = "PropertyDetails")
public class PropertyDetails {
	
	@Id
    private String id;
    
	private String title;
    
	private String description;
    
	private Integer price;
    
	private Type type;
    
	private String location;
    
    private String ownerId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateListed;
    
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	private String imageId;
    
    private byte[] imageData;

    public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String generateBase64Image() {
        return Base64.encodeBase64String(this.imageData);
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public Date getDateListed() {
		return dateListed;
	}
	public void setDateListed(Date dateListed) {
		this.dateListed = dateListed;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
