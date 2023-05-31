package com.example.spring_project.login;


import com.example.spring_project.facade.ClientFacade;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Scope("prototype")
public class LoginParameters {
    private Calendar calendar;
    private ClientFacade clientFacade;

    public LoginParameters(ClientFacade clientFacade) {
        this.calendar = Calendar.getInstance();
        this.clientFacade = clientFacade;
    }

    public LoginParameters() {
    }

    public void setClientFacade(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    public Calendar getDate() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public ClientFacade getClientFacade() {
        return clientFacade;
    }

    @Override
    public String toString() {
        return "LoginParameters{" +
                "calendar=" + calendar +
                ", clientFacade=" + clientFacade +
                '}';
    }
}
