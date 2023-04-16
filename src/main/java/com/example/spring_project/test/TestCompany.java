package com.example.spring_project.test;


import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.CompanyFacade;
import com.example.spring_project.login.ClientType;
import com.example.spring_project.login.LoginManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
@Service

public class TestCompany {
    private ApplicationContext ctx;
    private CompanyFacade companyFacade;

    public TestCompany(ApplicationContext ctx, CompanyFacade companyFacade) {
        this.ctx = ctx;
        this.companyFacade = companyFacade;
    }
    public void runAllCompanyFacadeTest() throws ExceptionCoupons {
        login();
        if (companyFacade != null) {
           addCoupon();
//            updateCoupon();
//            deleteCoupon();
//            getCompanyCoupons();
//            getCompanyCouponsByCategory();
//            getCompanyCouponsByMaxPrice();
//            getCompanyDetails();
        }
    }


    private void getCompanyDetails() throws ExceptionCoupons {
        System.out.println("📢get company details📢");
        System.out.println(companyFacade.getCompanyDetails());

    }

    private void getCompanyCouponsByMaxPrice() {
        System.out.println("📢get company coupons by maximum price📢");
        System.out.println(companyFacade.getAllCouponsByCompanyIdAndMaxPrice(7.9));

    }

    private void getCompanyCouponsByCategory() {
        System.out.println("📢get company coupons by category📢");
        System.out.println(companyFacade.getAllCouponsByCompanyIdAndCategory(Category.Food));
    }

    private void getCompanyCoupons() {
        System.out.println("📢get company coupons📢");
        System.out.println(companyFacade.getAllCouponsByCompanyId());
    }

    private void deleteCoupon() throws ExceptionCoupons {
        System.out.println("📢delete coupon📢");
        List<Coupon> coupons = companyFacade.getAllCouponsByCompanyId();
        companyFacade.deleteCouponById(coupons.get(0).getId());
        System.out.println("delete coupon success");



    }

    private void updateCoupon() throws ExceptionCoupons {
        System.out.println("📢update coupon📢");
        List<Coupon> coupons = companyFacade.getAllCouponsByCompanyId();
        Coupon coupon1 = coupons.get(3);
        coupon1.setDescription("Margareta as smart as 'menta'");
        companyFacade.updateCoupon(coupon1);
        System.out.println("update coupon success");

    }


    private void addCoupon() throws ExceptionCoupons {
        System.out.println("📢add coupon📢");

        Coupon coupon1 = new Coupon(companyFacade.getCompanyDetails(), Category.Food, "Margareta", "Big bos to sail",
                Date.valueOf("2023-02-08"), Date.valueOf("2023-09-02"), 2, 1.90, "🤬");
        Coupon coupon2 = new Coupon(companyFacade.getCompanyDetails(), Category.SPA, "Shraga", "Shraga is recruiting John Bryce graduates ",
                    Date.valueOf("2023-02-08"), Date.valueOf("2023-07-02"), 7, 8.90, "🤠");
        Coupon coupon3 = new Coupon(companyFacade.getCompanyDetails(), Category.SPORT, "Stam", "Achla shem shebaholam",
                    Date.valueOf("2023-02-08"), Date.valueOf("2023-06-02"), 8, 9.90, "😎");
        Coupon coupon4 = new Coupon(companyFacade.getCompanyDetails(), Category.BEVERAGES, "Yada", "yada yada",
                    Date.valueOf("2023-02-08"), Date.valueOf("2023-05-02"), 2, 10.90, "😴");
        Coupon coupon5 = new Coupon(companyFacade.getCompanyDetails(), Category.TRAVEL, "Bla", "bla bla",
                    Date.valueOf("2023-02-07"), Date.valueOf("2023-02-18"), 4, 6.90, "👽");
        companyFacade.addCoupon(coupon1);
        companyFacade.addCoupon(coupon2);
        companyFacade.addCoupon(coupon3);
        companyFacade.addCoupon(coupon4);
        companyFacade.addCoupon(coupon5);
        System.out.println("add coupon success");

    }

    private CompanyFacade login(){
        LoginManager loginManager=ctx.getBean(LoginManager.class);
        return companyFacade= (CompanyFacade) loginManager.login("Nirteck@gmail.com", "Nirteck1234", ClientType.Company);
    }
}
