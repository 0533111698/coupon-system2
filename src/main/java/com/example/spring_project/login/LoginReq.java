package com.example.spring_project.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
    private String email;
    private String password;
    private ClientType clientType;
}
