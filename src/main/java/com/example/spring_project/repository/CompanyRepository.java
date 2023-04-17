package com.example.spring_project.repository;

import com.example.spring_project.beans.Company;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.exception.ExceptionCoupons;
import org.hibernate.mapping.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    boolean existsByEmailOrName(String email, String name);
    boolean existsByEmailAndPassword(String email, String password);
    Company findByEmailAndPassword(String email, String password);

}
