package com.example.spring_project.controllers;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.CompanyFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private CompanyFacade companyFacade;

    public CompanyController(CompanyFacade companyFacade) {
        this.companyFacade = companyFacade;
    }
    @PostMapping("/addCoupon")
    public ResponseEntity<String>addCoupon(@RequestBody Coupon coupon){
        try {
            companyFacade.addCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED).body("Coupon added!");
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/updateCoupon")
    public ResponseEntity<String>updateCoupon(@RequestBody Coupon coupon){
        try {
            companyFacade.updateCoupon(coupon);
            return ResponseEntity.ok().body("Coupon update!");
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<String> deleteCouponById(@PathVariable int couponId){
        try {
            companyFacade.deleteCouponById(couponId);
            return ResponseEntity.ok().body("The coupon deleted!");
        }catch (ExceptionCoupons e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getAllCompanyCoup")
    public List<Coupon>getAllCompanyCoupons(){
        return companyFacade.getCompanyCoupons();
    }
    @GetMapping("/getAllCompanyCoupons")
    public List<Coupon>getAllCompanyCoupons(@RequestBody Category category){
        return companyFacade.getCompanyCoupons(category);
    }
    @GetMapping("/getAllCompanyCoupons/{maxPrice}")
    public List<Coupon>getAllCompanyCoupons(@PathVariable double maxPrice){
        return companyFacade.getCompanyCoupons(maxPrice);
    }
    @GetMapping("/getCompanyDetails")
    public ResponseEntity<?>getCompanyDetails(){
        try {
            return ResponseEntity.ok().body(companyFacade.getCompanyDetails());
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
