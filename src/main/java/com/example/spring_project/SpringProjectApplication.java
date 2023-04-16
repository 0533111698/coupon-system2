package com.example.spring_project;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Company;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.beans.Customer;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.AdminFacade;
import com.example.spring_project.facade.CompanyFacade;
import com.example.spring_project.facade.CustomerFacade;
import com.example.spring_project.login.ClientType;
import com.example.spring_project.login.LoginManager;
import com.example.spring_project.repository.CompanyRepository;
import com.example.spring_project.repository.CouponRepository;
import com.example.spring_project.test.TestAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Date;

@SpringBootApplication
public class SpringProjectApplication {

	public static void main(String[] args) throws ExceptionCoupons {
		ConfigurableApplicationContext ctx=SpringApplication.run(SpringProjectApplication.class, args);
		//try {
		TestAll testAll=ctx.getBean(TestAll.class);
		testAll.testAll();
		//} catch (ExceptionCoupons e) {
		//	System.out.println(e.getMessage());
		//}
		//private void login() {
		//        try {
		//            adminFacade= (AdminFacade) LoginManager.getInstance().login("admin@admin.com","admin", ClientType.Administrator);
		//        } catch (MyException | SQLException e) {
		//            System.out.println(e.getMessage());
		//        }
		//    }

	}

}
