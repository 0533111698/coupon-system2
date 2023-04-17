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

    /**
     * The method receives customer's email and password and checks if the email and password are correct,
     * by method 'iscustomerExists' from the dao
     * @param email customer's email
     * @param password customer's password
     * @return true or false
     */
    @Override
    public boolean login(String email, String password) {
        if (customerRepo.existsByEmailAndPassword(email, password)) {
            customerId = customerRepo.findByEmailAndPassword(email, password).getId();
            return true;
        }
        return false;
    }

    /**
     * The method receives a coupon object and checks if this customer has the same coupon,
     * if not,checks if this amount of coupons bigger of 0,
     * if yes checks if this end date of coupons is not over yet
     * if not the method adds it to 'coupons' db by the method 'addCouponPurchase' from the dao
     * @param coupon a coupon object
     * @throws ExceptionCoupons if the purchase is  exists for this customer or if the amount of coupons is not greater than 0 or if the end date of coupons is over
     */
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

    /**
     * The method receives a coupon object and checks if the customer have the purchase
     * if not, throw exception
     * if yes delete the purchase from DB and add 1 to the coupon amount and update it in DB
     * @param coupon coupon to delete purchase
     * @throws ExceptionCoupons if the customer don't have the coupon purchase
     */
    public void deletePurchaseCoupon(Coupon coupon) throws ExceptionCoupons {
        if (isPurchaseExists(coupon.getId())){
            Customer customerToUpdate = getCustomerDetails();
            Set<Coupon>coupons=customerToUpdate.getCoupons();
            coupons.remove(coupon);
            customerToUpdate.setCoupons(coupons);
            customerRepo.save(customerToUpdate);
            coupon.setAmount(coupon.getAmount()+1);
            couponRepo.save(coupon);
        }else {
            throw new ExceptionCoupons("The purchase is not exists");
        }
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

    /**
     *
     * @param category
     * @return
     * @throws ExceptionCoupons
     */
    public Set<Coupon> getCustomerCoupons(Category category) throws ExceptionCoupons {
        Set<Coupon> couponList = getCustomerCoupons();
        couponList.removeIf(cou -> !cou.getCategory().equals(category));
        return couponList;
    }
    public Set<Coupon> getCustomerCoupons(double maxPrice) throws ExceptionCoupons {
        Set<Coupon> couponList = getCustomerCoupons();
        couponList.removeIf(cou -> cou.getPrice()>(maxPrice));
        return couponList;
    }
    public List<Coupon>getAllCoupons(){
        return couponRepo.findAll();
    }



}
