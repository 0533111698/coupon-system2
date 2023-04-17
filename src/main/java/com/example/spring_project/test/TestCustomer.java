package com.example.spring_project.test;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.CompanyFacade;
import com.example.spring_project.facade.CustomerFacade;
import com.example.spring_project.login.ClientType;
import com.example.spring_project.login.LoginManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TestCustomer {
    private CustomerFacade customerFacade;
    private ApplicationContext ctx;

    public TestCustomer(CustomerFacade customerFacade, ApplicationContext ctx) {
        this.customerFacade = customerFacade;
        this.ctx = ctx;
    }

    public void runAllCustomerFacadeTest() throws ExceptionCoupons {
        login();
        if (customerFacade != null) {
            purchaseCoupon();
//            getCustomerCoupons();
//           getCustomerCouponsByCategory();
//          getCustomerCouponsByMaxPrice();
//        getAllCoupons();
//      getCustomerDetails();
        }

    }

    private void getCustomerDetails() throws ExceptionCoupons {
        System.out.println("📢get customer details ");
        System.out.println(customerFacade.getCustomerDetails());


    }

    private void getAllCoupons() {
        System.out.println("📢get all coupons ");
            System.out.println(customerFacade.getAllCoupons());
    }

    private void getCustomerCouponsByMaxPrice() throws ExceptionCoupons {
        System.out.println("📢get customer coupons by maximum price");
            System.out.println(customerFacade.getCustomerCoupons(10.9));

    }

    private void getCustomerCouponsByCategory() throws ExceptionCoupons {
        System.out.println("📢get customer coupons by category📢");
            System.out.println(customerFacade.getCustomerCoupons(Category.Food));
    }

    private void getCustomerCoupons() throws ExceptionCoupons {
        System.out.println("📢get customer coupons📢");
            System.out.println(customerFacade.getCustomerCoupons());

    }

    public void deletePurchaseCoupon() throws ExceptionCoupons {
        System.out.println("📢delete purchase coupon📢");
        List<Coupon> coupons = customerFacade.getAllCoupons();
            customerFacade.deletePurchaseCoupon(coupons.get(0));
            System.out.println("delete purchase success");
    }

    private void purchaseCoupon() throws ExceptionCoupons {
        System.out.println("📢purchase coupon📢");
            List<Coupon> coupons = customerFacade.getAllCoupons();
            customerFacade.purchaseCoupon(coupons.get(0));
            System.out.println("purchase success");
    }

    private CustomerFacade login() {
        LoginManager loginManager=ctx.getBean(LoginManager.class);
        return customerFacade= (CustomerFacade) loginManager.login("malki@gmail.com", "malki1234", ClientType.Customer);


    }



}
