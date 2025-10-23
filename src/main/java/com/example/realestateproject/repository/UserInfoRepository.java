
package com.example.realestateproject.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.realestateproject.entity.UserInfo;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo,String> {

    //custom method to finduserby username
    Optional<UserInfo> findByUsername(String username);

}
