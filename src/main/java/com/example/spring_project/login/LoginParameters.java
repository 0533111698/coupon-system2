package com.example.spring_project.login;

import com.auth0.jwt.JWT;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
@Component
public class LoginParameters {
    private Calendar calendar;
    private ClientType clientType;

    public LoginParameters(String token) {
        this.calendar =Calendar.getInstance();
        this.clientType = ClientType.valueOf(JWT.decode(token).getClaim("clientType").asString());
    }

    public LoginParameters() {
    }

    public Calendar getDate() {
        return calendar;
    }

    public ClientType getClientType() {
        return clientType;
    }

    @Override
    public String toString() {
        return "LoginParameters{" +
                "date=" + calendar +
                ", clientType=" + clientType +
                '}';
    }
}
