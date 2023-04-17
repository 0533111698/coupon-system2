package com.example.spring_project.facade;

import com.example.spring_project.repository.CompanyRepository;
import com.example.spring_project.repository.CouponRepository;
import com.example.spring_project.repository.CustomerRepository;



public abstract class ClientFacade {
    protected CustomerRepository customerRepo;
    protected CouponRepository couponRepo;
    protected CompanyRepository companyRepo;
    public abstract boolean login (String email,String password);

    public ClientFacade(CustomerRepository customerRepository, CouponRepository couponRepository, CompanyRepository companyRepository) {
        this.customerRepo = customerRepository;
        this.couponRepo = couponRepository;
        this.companyRepo = companyRepository;
    }
}