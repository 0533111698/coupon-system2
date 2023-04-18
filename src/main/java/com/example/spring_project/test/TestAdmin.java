package com.example.spring_project.test;

import com.example.spring_project.beans.Category;
import com.example.spring_project.beans.Company;
import com.example.spring_project.beans.Customer;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.facade.AdminFacade;
import com.example.spring_project.login.ClientType;
import com.example.spring_project.login.LoginManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.sql.SQLException;
import java.util.List;

@Service
public class TestAdmin {
    private AdminFacade adminFacade;
    private ApplicationContext ctx;

    public TestAdmin(AdminFacade adminFacade, ApplicationContext ctx) {
        this.adminFacade = adminFacade;
        this.ctx = ctx;
    }
    public void runAllAdminFacadeTest() throws ExceptionCoupons {
        login();
        if (adminFacade!=null){
//               addCompany();
//            updateCompany();
//           deleteCompany();
//         getAllCompanies();
//            getOneCompany();
//           addCustomer();
//            updateCustomer();
            deleteCustomer();
//            getAllCustomers();
//            getOneCustomer();

        }
    }

    private void getOneCustomer() throws ExceptionCoupons {
        System.out.println("📢getOneCustomer📢");
            List<Customer> customers=adminFacade.getAllCustomers();
            System.out.println(adminFacade.getOneCustomer(customers.get(0).getId()));

    }

    private void getAllCustomers() {
        System.out.println("📢getAllCustomers📢");
        System.out.println(adminFacade.getAllCustomers());
    }

    private void deleteCustomer() throws ExceptionCoupons {
        System.out.println("📢delete customer📢");
            List<Customer>customers=adminFacade.getAllCustomers();
            adminFacade.deleteCustomer(customers.get(0).getId());

    }

    private void updateCustomer() throws ExceptionCoupons {
        System.out.println("📢updateCustomer📢");
            List<Customer>customers=adminFacade.getAllCustomers();
            Customer customer1=customers.get(0);
            customer1.setEmail("9876@gmail.com");
            Customer customer2=customers.get(1);
            customer2.setFirstName("Aviadush");
            Customer customer3=customers.get(2);
            customer3.setLastName("Danino");
            adminFacade.updateCustomer(customer1);
            adminFacade.updateCustomer(customer2);
            adminFacade.updateCustomer(customer3);

    }

    private void addCustomer() throws ExceptionCoupons {
        System.out.println("📢addCustomer📢");
        Customer customer1=new Customer("Malki","Gefner","malki@gmail.com","malki1234");
        Customer customer2=new Customer("Aviad","Barel","Aviad@gmail.com","Aviad1234");
        Customer customer3=new Customer("Esti","Rayniz","Esti@gmail.com","Esti1234");
        Customer customer4=new Customer("Nir","Gal","Nir@gmail.com","Nir1234");
        Customer customer5=new Customer("Buchanan","Gershoni","Buchanan@gmail.com","malki1234");
        adminFacade.addCustomer(customer1);
        adminFacade.addCustomer(customer2);
        adminFacade.addCustomer(customer3);
        adminFacade.addCustomer(customer4);
        adminFacade.addCustomer(customer5);


    }

    private void getOneCompany() throws ExceptionCoupons {
        System.out.println("📢getOneCompany📢");
        List<Company>companies=adminFacade.getAllCompanies();
        System.out.println(adminFacade.getOneCompany(companies.get(0).getId()));

    }

    private void getAllCompanies() {
        System.out.println("📢getAllCompanies📢");
        System.out.println(adminFacade.getAllCompanies());

    }

    private void deleteCompany() throws ExceptionCoupons {
        System.out.println("📢delete company📢");
        List<Company>companies=adminFacade.getAllCompanies();
        adminFacade.deleteCompany(companies.get(0).getId());

    }

    private void updateCompany() throws ExceptionCoupons {
        System.out.println("📢update company📢");
        List<Company>companies=adminFacade.getAllCompanies();
        Company company1=adminFacade.getOneCompany(companies.get(0).getId());
        company1.setPassword("Baby6789");
        Company company2=adminFacade.getOneCompany(companies.get(1).getId());
        company2.setPassword("Ariel6789");
        Company company3=adminFacade.getOneCompany(companies.get(2).getId());
        company3.setPassword("Elite6789");
        adminFacade.updateCompany(company1);
        adminFacade.updateCompany(company2);
        adminFacade.updateCompany(company3);
    }

    private void addCompany() throws ExceptionCoupons {
        System.out.println("📢add company📢");
        Company company1=new Company("Baby Star","Baby@gmail.com","vvv1234");
        Company company2=new Company("Ariel","Ariel@gmail.com","Ariel1234");
        Company company3=new Company("Elite","Elite@gmail.com","Elite1234");
        Company company4=new Company("Tnuva","Tnuva@gmail.com","Tnuva1234");
        Company company5=new Company("Nirteck","Nirteck@gmail.com","Nirteck1234");
        adminFacade.addCompany(company1);
        adminFacade.addCompany(company2);
        adminFacade.addCompany(company3);
        adminFacade.addCompany(company4);
        adminFacade.addCompany(company5);


    }

    private AdminFacade login() {
        LoginManager loginManager=ctx.getBean(LoginManager.class);
       return adminFacade= (AdminFacade) loginManager.login("admin@admin.com","admin", ClientType.Administrator);
    }

}
