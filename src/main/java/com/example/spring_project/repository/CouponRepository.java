package com.example.spring_project.repository;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.exception.ExceptionCoupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {
    List<Coupon> findByCompanyId(int companyId);
    List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);
    List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double maxPrice);
    boolean existsByTitleAndCompanyId(String title,int companyId);
    void deleteByCompanyId(int companyId) throws ExceptionCoupons;

    @Modifying
    @Query(value = "DELETE FROM `couponsproject`.`customers_coupons` WHERE (`customer_id` = ?) and (`coupons_id` = ?)",nativeQuery = true)
    void deleteCouponPurchase(int customerId, int couponId) throws ExceptionCoupons;
    @Modifying
    @Query(value = "DELETE FROM `couponsproject`.`customers_coupons` WHERE (`coupons_id` = ?)",nativeQuery = true)
    void deleteCouponPurchaseByCouponId(int couponId) throws ExceptionCoupons;
    @Modifying
    @Query(value = "DELETE FROM `couponsproject`.`customers_coupons` WHERE (`customer_id` = ?)",nativeQuery = true)
    void deleteCouponPurchaseByCustomerId(int customerId) throws ExceptionCoupons;
}
