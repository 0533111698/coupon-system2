package com.example.spring_project.login;

import com.example.spring_project.SpringProjectApplication;
import com.example.spring_project.facade.AdminFacade;
import com.example.spring_project.facade.ClientFacade;
import com.example.spring_project.facade.CompanyFacade;
import com.example.spring_project.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;


@Service
public class LoginManager {
    private ApplicationContext ctx;

    public LoginManager(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public ClientFacade login(String email, String password, ClientType clientType) {
        switch (clientType){
            case Administrator:
                AdminFacade adminFacade = ctx.getBean(AdminFacade.class);
                if (adminFacade.login(email,password)) {
                    return adminFacade;
                }
            case Company:
                CompanyFacade companyFacade =ctx.getBean(CompanyFacade.class);
                if (companyFacade.login(email,password))
                    return companyFacade;
            case Customer:
                CustomerFacade customerFacade = ctx.getBean(CustomerFacade.class);
                if (customerFacade.login(email,password))
                    return customerFacade;
        }
        return null;
    }
}
