package com.example.spring_project.controllers;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.CustomerFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerFacade customerFacade;

    public CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }
    @PostMapping("/purchaseCoupon")
    public ResponseEntity<String>purchaseCoupon(@RequestBody Coupon coupon ){
        try {
            customerFacade.purchaseCoupon(coupon);
            return ResponseEntity.ok().body("The Coupon purchase successfully!");
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/deletePurchaseCoupon")
    public ResponseEntity<String>deletePurchaseCoupon(@RequestBody Coupon coupon){
        try {
            customerFacade.deletePurchaseCoupon(coupon);
            return ResponseEntity.ok().body("The purchase deleted!");

        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?>getCustomerCoupons(){
        try {
            return ResponseEntity.ok().body(customerFacade.getCustomerCoupons());
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getCustomerCouponsByCategory")
    public ResponseEntity<?>getCustomerCouponsByCategory(@RequestBody Category category){
        try {
            return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(category));
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getCustomerCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?>getCustomerCouponsByCategory(@PathVariable double maxPrice){
        try {
            return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(maxPrice));
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public List<Coupon> getAllCoupons(){
        return customerFacade.getAllCoupons();
    }
}
