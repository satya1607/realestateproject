package com.example.realestateproject.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.realestateproject.enums.UserRole;



@Document(collection="UserInfo")
public class UserInfo {
	    
	@Id
    private String _id;

    @Indexed(unique = true) 
    private Long id; 
	
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
    public UserInfo(Long id, String username, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
