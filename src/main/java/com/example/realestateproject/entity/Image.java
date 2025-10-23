package com.example.realestateproject.entity;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
public class Image {
	@Id
    private String id;
  
    
    private String imageName;
    
	private byte[] data;
	
	 public String getDataBase64() {
	        return Base64.encodeBase64String(this.data);
	    } 
	
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
}
