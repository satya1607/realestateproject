
package com.example.realestateproject.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.realestateproject.entity.UserInfo;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo,ObjectId> {

    Optional<UserInfo> findByUsername(String username);

}
