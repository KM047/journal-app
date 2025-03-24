package com.kunal.journalApp.models;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersModel {

    @Id
    private ObjectId id;
    @Indexed(unique = true)  // For this you have to enable index in application.properties file "spring.data.mongodb.auto-index-creation=true"
    @NonNull
    private String username;
    @Indexed(unique = true)
    @NonNull
    private String email;
    @NonNull
    private String password;

    private boolean sentimentAnalysis;

    @DBRef
    private List<JournalEntryModel> journalEntries = new ArrayList<>();

    private List<String> roles;

}
