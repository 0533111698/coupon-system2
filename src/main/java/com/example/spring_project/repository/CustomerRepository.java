package com.example.spring_project.repository;

import com.example.spring_project.beans.Company;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Customer findByEmailAndPassword(String email, String password);
}
