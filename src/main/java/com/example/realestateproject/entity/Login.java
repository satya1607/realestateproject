package com.example.realestateproject.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Login")
public class Login {
	
	private String username;
	private String password;
  
   public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

   
}
