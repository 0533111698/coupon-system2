package com.example.spring_project.facade;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.beans.Customer;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.repository.CompanyRepository;
import com.example.spring_project.repository.CouponRepository;
import com.example.spring_project.repository.CustomerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Scope("prototype")
public class CustomerFacade extends ClientFacade{
    private int customerId;


    public CustomerFacade(CustomerRepository customerRepository, CouponRepository couponRepository, CompanyRepository companyRepository) {
        super(customerRepository, couponRepository, companyRepository);
    }
    @Override
    public boolean login(String email, String password) {
        if (customerRepo.existsByEmailAndPassword(email, password)) {
            customerId = customerRepo.findByEmailAndPassword(email, password).getId();
            return true;
        }
        return false;
    }
    public void purchaseCoupon(Coupon coupon) throws ExceptionCoupons {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new Date(millis);
        if (!isPurchaseExists(coupon.getId())){
            if (coupon.getId()>0){
                if (coupon.getEndDate().after(date)){
                    Customer customerToUpdate=getCustomerDetails();
                    Set<Coupon>coupons=customerToUpdate.getCoupons();
                    coupons.add(coupon);
                    customerToUpdate.setCoupons(coupons);
                    customerRepo.save(customerToUpdate);
                    coupon.setAmount(coupon.getAmount()-1);
                    couponRepo.save(coupon);
                } else
                    throw new ExceptionCoupons("The coupon is expired");
            } else
                throw new ExceptionCoupons("The coupon is out of stock");
        } else
            throw new ExceptionCoupons("you already bought the coupon");
    }
    public Set<Coupon>getCustomerCoupons() throws ExceptionCoupons {
      return getCustomerDetails().getCoupons();
    }

    private boolean isPurchaseExists(int id) throws ExceptionCoupons {
        Set<Coupon> coupons=getCustomerDetails().getCoupons();
        for (Coupon coupon:coupons){
            if (coupon.getId()==id)
                return true;
        }
        return false;
    }
    public Customer getCustomerDetails() throws ExceptionCoupons {
       return customerRepo.findById(customerId).orElseThrow(()->new ExceptionCoupons("The customer not exist"));
    }
    public Set<Coupon> getCustomerCouponsByCategory(Category category) throws ExceptionCoupons {
        Set<Coupon> couponList = getCustomerCoupons();
        couponList.removeIf(cou -> !cou.getCategory().equals(category));
        return couponList;
    }
    public Set<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws ExceptionCoupons {
        Set<Coupon> couponList = getCustomerCoupons();
        couponList.removeIf(cou -> cou.getPrice()>(maxPrice));
        return couponList;
    }
    public List<Coupon>getAllCoupons(){
        return couponRepo.findAll();
    }



}
