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
    /**
     * the method check if there is company that have the email or the name in the DB
     * @param email the company email
     * @param name the company name
     * @return true or false
     */
    boolean existsByEmailOrName(String email, String name);

    /**
     * the method check if the email and the password are in the DB
     * @param email company email
     * @param password company password
     * @return ture or false
     */
    boolean existsByEmailAndPassword(String email, String password);

    /**
     * the method find company by email and password
     * @param email company email
     * @param password company password
     * @return company
     */
    Company findByEmailAndPassword(String email, String password);

}
