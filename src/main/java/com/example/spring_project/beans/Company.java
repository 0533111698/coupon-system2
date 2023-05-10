package com.example.spring_project.beans;

import com.example.spring_project.exception.ExceptionCoupons;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "companies")
@ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name, email, password;
    @JsonIgnore
    @OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
    private List<Coupon> coupons;

    public Company() {
    }

    public Company(String name, String email, String password) throws ExceptionCoupons {
        this.name = name;
        setEmail(email);
        setPassword(password);
    }

    public Company(int id, String name, String email, String password, List<Coupon> coupons) throws ExceptionCoupons {
        this.id = id;
        this.name = name;
        setEmail(email);
        setPassword(password);
        this.coupons = coupons;
    }

    public Company(int id, String name, String email, String password) throws ExceptionCoupons {
        this.id = id;
        this.name = name;
        setEmail(email);
        setPassword(password);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ExceptionCoupons {
        if (email.contains("@"))
            this.email = email;
        else throw new ExceptionCoupons("invalid mail");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
