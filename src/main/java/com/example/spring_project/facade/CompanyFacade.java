package com.example.spring_project.facade;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Company;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.beans.Customer;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.repository.CompanyRepository;
import com.example.spring_project.repository.CouponRepository;
import com.example.spring_project.repository.CustomerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Service
@Scope("prototype")
public class CompanyFacade extends ClientFacade{
    private int companyId;
    public CompanyFacade(CustomerRepository customerRepository, CouponRepository couponRepository, CompanyRepository companyRepository) {
        super(customerRepository, couponRepository, companyRepository);
    }

    @Override
    public boolean login(String email, String password) {
        if (companyRepo.existsByEmailAndPassword(email,password)){
            companyId=companyRepo.findByEmailAndPassword(email,password).getId();
            return true;
        }
        return false;
    }
    public void addCoupon(Coupon coupon) throws ExceptionCoupons {
        if (couponRepo.existsByTitleAndCompanyId(coupon.getTitle(),coupon.getCompany().getId())) {
            throw new ExceptionCoupons("This title is exists for your company");
        }
        couponRepo.save(coupon);
    }
    public void updateCoupon(Coupon coupon) throws ExceptionCoupons {
        if (couponRepo.existsById(coupon.getId())) {
            Coupon couponFromDB=couponRepo.findById(coupon.getId()).orElseThrow(()->new ExceptionCoupons("The coupon not exists"));
            if (couponFromDB.getCompany().getId()==coupon.getCompany().getId())
                couponRepo.save(coupon);
            else
                throw new ExceptionCoupons("The company id cannot be updated");
        }
        else
        throw new ExceptionCoupons("coupon is not exists");
    }
    public void deleteCouponById(int couponId) throws ExceptionCoupons {
        if (couponRepo.existsById(couponId)) {
            List<Customer> customers = customerRepo.findAll();
            for (Customer cus : customers) {
                Set<Coupon> coupons = cus.getCoupons();
                coupons.removeIf(coup -> coup.getId() == couponId);
                cus.setCoupons(coupons);
                customerRepo.save(cus);
            }
            couponRepo.deleteById(couponId);
        }
        else
        throw new ExceptionCoupons("The company delete is failed");
    }
    public List<Coupon>getAllCouponsByCompanyId(){
        return couponRepo.findByCompanyId(companyId);
    }
    public List<Coupon>getAllCouponsByCompanyIdAndCategory(Category category){
        return couponRepo.findByCompanyIdAndCategory(companyId,category);
    }
    public List<Coupon>getAllCouponsByCompanyIdAndMaxPrice(double maxPrice){
        return couponRepo.findByCompanyIdAndPriceLessThanEqual(companyId,maxPrice);
    }
    public Company getCompanyDetails() throws ExceptionCoupons {
       return companyRepo.findById(companyId).orElseThrow(()->new ExceptionCoupons("The company not exist!"));
    }

}
