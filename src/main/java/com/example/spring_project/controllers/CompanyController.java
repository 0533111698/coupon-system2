package com.example.spring_project.controllers;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.exception.ExceptionAuth;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.CompanyFacade;
import com.example.spring_project.login.LoginParameters;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HashMap<String, LoginParameters> sessions;
    private CompanyFacade getCompanyFacade() throws ExceptionAuth {
        String token=request.getHeader("authorization");
        token = token.replace("Bearer ","");
      //  if (sessions.containsKey(token)) {
            sessions.get(token).setCalendar(Calendar.getInstance());
            return (CompanyFacade) sessions.get(token).getClientFacade();
//        }
      //  else
//            throw new ExceptionAuth();

    }


    @PostMapping("/addCoupon")
    public ResponseEntity<?>addCoupon(@RequestBody Coupon coupon){
        try {
            getCompanyFacade().addCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED).body(coupon);
        } catch (ExceptionAuth | ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/updateCoupon")
    public ResponseEntity<?>updateCoupon(@RequestBody Coupon coupon){
        try {
            getCompanyFacade().updateCoupon(coupon);
            return ResponseEntity.ok().body(coupon);
        } catch (ExceptionAuth|ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<String> deleteCouponById(@PathVariable int couponId){
        try {
            getCompanyFacade().deleteCouponById(couponId);
            return ResponseEntity.ok().body("The coupon deleted!");
        }catch (ExceptionAuth|ExceptionCoupons e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getAllCompanyCoup")
    public ResponseEntity<?>getAllCompanyCoupons(){
        try {
            return ResponseEntity.ok().body( getCompanyFacade().getCompanyCoupons());
        } catch (ExceptionAuth e) {
           return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    @GetMapping("/getAllCompanyCoupons")
    public ResponseEntity<?>getAllCompanyCoupons(@RequestBody Category category){
        try {
            return ResponseEntity.ok().body( getCompanyFacade().getCompanyCoupons(category));
        } catch (ExceptionAuth e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    @GetMapping("/getAllCompanyCoupons/{maxPrice}")
    public ResponseEntity<?>getAllCompanyCoupons(@PathVariable double maxPrice){
        try {
            return ResponseEntity.ok().body(getCompanyFacade().getCompanyCoupons(maxPrice));
        } catch (ExceptionAuth e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    @GetMapping("/getCompanyDetails")
    public ResponseEntity<?>getCompanyDetails(){
        try {
            return ResponseEntity.ok().body(getCompanyFacade().getCompanyDetails());
        } catch (ExceptionAuth|ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
