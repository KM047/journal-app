package com.kunal.journalApp.repository;

import com.kunal.journalApp.models.UsersModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UsersModel, ObjectId> {

    UsersModel findByUsername(String username);


    void deleteByUsername(String username);
}
