package com.example.realestateproject.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.realestateproject.enums.UserRole;

@Document(collection="UserInfo")
public class UserInfo {
	    
    @Id 
    private ObjectId id; 
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	private String name;
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String username;
    private  String password;
    private UserRole role;
//    private  String roles;

//default constructor
    public UserInfo(){

    }
    
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	

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
	
	//paremeter constructor
    public UserInfo(ObjectId id, String username, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
