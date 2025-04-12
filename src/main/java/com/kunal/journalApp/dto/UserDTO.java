package com.kunal.journalApp.dto;

import com.kunal.journalApp.models.UsersModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private ObjectId id;
    private String email;
    private String username;
    private String password;
    private List<String> roles;

    public UserDTO(UsersModel userModel) {
        this.id = userModel.getId();
        this.email = userModel.getEmail();
        this.username = userModel.getUsername();
        this.roles = userModel.getRoles();
        this.password = userModel.getPassword();
    }
}
