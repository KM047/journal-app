package com.kunal.journalApp.repository;

import com.kunal.journalApp.models.JournalEntryModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntryModel, ObjectId> {


}
