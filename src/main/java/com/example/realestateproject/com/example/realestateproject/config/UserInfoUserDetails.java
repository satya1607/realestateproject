package com.example.realestateproject.com.example.realestateproject.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.realestateproject.entity.UserInfo;

//public class UserInfoUserDetails implements UserDetails {
//     private UserInfo userInfo;
//
////    private  String name;
////    private  String password;
//    //roles-
////    private List<GrantedAuthority> authorities;
//
//
//    //constructor->Userinfo->from-DB to your spirng security format
//
//
//    public UserInfoUserDetails(UserInfo userInfo) {
//    	this.userInfo=userInfo;
////        name=userInfo.getUsername();
////        password=userInfo.getPassword();
////
////        //split roles
////        authorities= Arrays.stream(userInfo.getRoles().split(","))
////                .map(SimpleGrantedAuthority::new)
////                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//    	 List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//         
//         authorities.add(new SimpleGrantedAuthority(userInfo.getRoles().toString()));
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return userInfo.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return userInfo.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//}
