package com.calis100.CalisAPI.dto;


import com.calis100.CalisAPI.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;

}
