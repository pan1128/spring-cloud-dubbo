package com.pan.userservice.mongodbdao;

import com.pan.userservice.entity.MongodbUser;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongodbUserRepository extends MongoRepository<MongodbUser,String> {
}
