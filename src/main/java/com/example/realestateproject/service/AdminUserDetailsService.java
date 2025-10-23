//package com.example.realestateproject.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
////import com.example.realestateproject.com.example.realestateproject.config.AdminUserDetails;
//import com.example.realestateproject.com.example.realestateproject.config.UserInfoUserDetails;
//import com.example.realestateproject.entity.Admin;
//import com.example.realestateproject.repository.AdminRepository;

//@Component
//public class AdminUserDetailsService  implements UserDetailsService {
// 
//    @Autowired 
//    private AdminRepository repo;
//     
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Admin admin = repo.findByUsername(username);
//        if (admin == null) {
//            throw new UsernameNotFoundException("No admin found with the given username.");
//        }
//         
////        return new AdminUserDetails(admin);
//    
//
//    return User.withUsername(admin.getUsername())
//            .password(admin.getPassword())
//            .roles(admin.getRoles().replace("ROLE_",""))//ADMIN,USER
//            .build();
//    }
//}
