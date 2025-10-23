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
	
//	location,price,type,keywords
	
	@Id
    private String _id;

    @Indexed(unique = true) 
    private Long id;
    
	private String title;
    
	private String description;
    
	private int price;
    
	private Type type;
    
	private String location;
    
    private String ownerId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateListed;
    
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
    
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
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
