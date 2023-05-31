package com.example.spring_project.controllers;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.exception.ExceptionAuth;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.CompanyFacade;
import com.example.spring_project.facade.CustomerFacade;
import com.example.spring_project.login.LoginParameters;
import com.example.spring_project.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    protected CouponRepository couponRepo;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HashMap<String, LoginParameters> sessions;
    private CustomerFacade getCustomerFacade()throws ExceptionAuth {
        String token = request.getHeader("authorization");
        token = token.replace("Bearer ", "");
        sessions.get(token).setCalendar(Calendar.getInstance());
        return (CustomerFacade) sessions.get(token).getClientFacade();
    }

    @PostMapping("/purchaseCoupon")
    public ResponseEntity<String>purchaseCoupon(@RequestBody Coupon coupon ){
        try {
            getCustomerFacade().purchaseCoupon(coupon);
            return ResponseEntity.ok().body("The Coupon purchase successfully!");
        } catch (ExceptionCoupons | ExceptionAuth e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/deletePurchaseCoupon")
    public ResponseEntity<String>deletePurchaseCoupon(@RequestBody Coupon coupon){
        try {
            getCustomerFacade().deletePurchaseCoupon(coupon);
            return ResponseEntity.ok().body("The purchase deleted!");

        } catch (ExceptionCoupons | ExceptionAuth e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?>getCustomerCoupons(){
        try {
            return ResponseEntity.ok().body(getCustomerFacade().getCustomerCoupons());
        } catch (ExceptionCoupons | ExceptionAuth e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getCustomerCouponsByCategory")
    public ResponseEntity<?>getCustomerCouponsByCategory(@RequestBody Category category){
        try {
            return ResponseEntity.ok().body(getCustomerFacade().getCustomerCoupons(category));
        } catch (ExceptionCoupons | ExceptionAuth e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getCustomerCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?>getCustomerCouponsByCategory(@PathVariable double maxPrice){
        try {
            return ResponseEntity.ok().body(getCustomerFacade().getCustomerCoupons(maxPrice));
        } catch (ExceptionCoupons | ExceptionAuth e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public List<Coupon> getAllCoupons()  {

        return couponRepo.findAll();
    }
}
