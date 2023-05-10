package com.example.spring_project.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.spring_project.SpringProjectApplication;
import com.example.spring_project.beans.Company;
import com.example.spring_project.beans.Customer;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.ClientFacade;
import com.example.spring_project.facade.CompanyFacade;
import com.example.spring_project.login.*;
import com.example.spring_project.repository.CompanyRepository;
import com.example.spring_project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("auth")
public class LoginManagerController {
    @Autowired
    private ApplicationContext ctx;




    @Autowired
    private LoginManager loginManager;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
        LoginManager loginManager=ctx.getBean(LoginManager.class);
        SpringProjectApplication spa=ctx.getBean(SpringProjectApplication.class);
        ClientFacade clientFacade = loginManager.login(loginReq.getEmail(), loginReq.getPassword(), loginReq.getClientType());
            if (clientFacade != null) {
                String token=createToken(loginReq);
                    spa.sessions().put(token, new LoginParameters(token));
                System.out.println(spa.sessions().toString());

                    return ResponseEntity.ok().body(token);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you are not register!");


    }
//    @GetMapping
//    public ResponseEntity<?>bla(HttpServletRequest request){
//        String token =request.getHeader("Authorization");
//        try {
//            String name = JWT.decode(token.replace("Bearer ", "")).getClaim("name").asString();
//            return ResponseEntity.ok(name);
//        }
//        catch (Exception e) {
//            return ResponseEntity.status(401).body("Eror, please login first");
//        }
//    }
    private String createToken(LoginReq loginReq){
        String token=null;
        switch (loginReq.getClientType()){
            case Administrator:
                token = JWT.create()
                        .withClaim("name","Big Boss")
                        .withClaim("clientType","Administrator")
                        .withClaim("email","admin@gmail.com")
                        .withIssuer("ginger coupon")
                        .withIssuedAt(new Date())
                        .sign(Algorithm.none());
                break;

            case Company:
                Company company=companyRepository.findByEmailAndPassword(loginReq.getEmail(),loginReq.getPassword());

                 token = JWT.create()
                        .withClaim("name",company.getName())
                         .withClaim("clientType","Company")
                        .withClaim("email",company.getEmail())
                        .withIssuer("ginger coupon")
                        .withIssuedAt(new Date())
                        .sign(Algorithm.none());
                 break;
            case Customer:
                Customer customer=customerRepository.findByEmailAndPassword(loginReq.getEmail(),loginReq.getPassword());

                token = JWT.create()
                        .withClaim("name",customer.getFirstName()+" "+customer.getLastName())
                        .withClaim("clientType","Customer")
                        .withClaim("email",customer.getEmail())
                        .withIssuer("ginger coupon")
                        .withIssuedAt(new Date())
                        .sign(Algorithm.none());
                break;


        }
        return token;

    }

}
