package com.example.spring_project.controllers;

import com.example.spring_project.beans.Company;
import com.example.spring_project.beans.Customer;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.AdminFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")

public class AdminController {
    private AdminFacade adminFacade;

    public AdminController(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }
    @PostMapping("/addCompany")
    public ResponseEntity<String >addCompany(@RequestBody Company company){
     try {
         adminFacade.addCompany(company);
         return ResponseEntity.status(HttpStatus.CREATED).body("company added!");
     } catch (ExceptionCoupons e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
     }
    }
    @PutMapping("/updateCompany")
    public ResponseEntity<String>updateCompany(@RequestBody Company company){
        try {
            adminFacade.updateCompany(company);
            return ResponseEntity.ok().body("company update!");
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/deleteCompany/{companyId}")
    public ResponseEntity<String>deleteCompany(@PathVariable int companyId){
        try {
            adminFacade.deleteCompany(companyId);
            return ResponseEntity.ok().body("The company deleted!");
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    @GetMapping("/getAllCompanies")
    public List<Company> getAllCompanies(){

        return adminFacade.getAllCompanies();
    }
    @GetMapping("/getOneCompany/{companyId}")
    public ResponseEntity <?> getOneCompany(@PathVariable int companyId){
        try {
            return ResponseEntity.ok().body(adminFacade.getOneCompany(companyId));
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/addCustomer")
    public ResponseEntity<String>addCustomer(@RequestBody Customer customer){
        try {
            adminFacade.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer added!");
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/updateCustomer")
    public ResponseEntity<String>updateCustomer(@RequestBody Customer customer){
        try {
            adminFacade.updateCustomer(customer);
            return ResponseEntity.ok().body("Customer update!");
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<String>deleteCustomer(@PathVariable int customerId){
        try {
            adminFacade.deleteCustomer(customerId);
            return ResponseEntity.ok().body("Company deleted!" );
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getAllCustomers")
    public List<Customer>getAllCustomers(){
        return adminFacade.getAllCustomers();
    }
    @GetMapping("/getOneCustomer/{customerId}")
    public ResponseEntity<?>getOneCustomer(@PathVariable int customerId){
        try {
            return ResponseEntity.ok().body(adminFacade.getOneCustomer(customerId));
        } catch (ExceptionCoupons e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
