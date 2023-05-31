package com.example.spring_project.daily_JOB;

import com.example.spring_project.SpringProjectApplication;
import com.example.spring_project.exception.ExceptionAuth;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.login.LoginParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;

@Service
public class TokenThread implements Runnable {

    @Autowired
    private HashMap<String, LoginParameters> sessions;
    @Override
    public void run() {
        while (true) {
            try {
                checkTokens();
                Thread.sleep(1000*60);
            } catch (InterruptedException ignored) {
            }
        }
    }

        public void checkTokens(){
            Calendar calendar=Calendar.getInstance();
            sessions.values().removeIf(s -> calendar.getTimeInMillis()-s.getDate().getTimeInMillis()>1000*60*30);


        }


}
