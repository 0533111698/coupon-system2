package com.example.spring_project.facade;

import com.example.spring_project.beans.Company;
import com.example.spring_project.beans.Coupon;
import com.example.spring_project.beans.Customer;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.repository.CompanyRepository;
import com.example.spring_project.repository.CouponRepository;
import com.example.spring_project.repository.CustomerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Transactional
@Service
@Scope("prototype")

public class AdminFacade extends ClientFacade{
    public AdminFacade(CustomerRepository customerRepository, CouponRepository couponRepository, CompanyRepository companyRepository) {
        super(customerRepository, couponRepository, companyRepository);
    }

    /**
     * The method receives admin's email and password and checks if they are correct
     * @param email admin's email
     * @param password admin's password
     * @return true or false
     */

    @Override
    public boolean login(String email, String password){
        return email.equals("admin@admin.com")&password.equals("admin");

    }

    /**
     * The method receives object company and checks if the mail or email exist in the 'companies' db,
     * if not the method adds the company to the 'companies' db
     * @param company a company object
     * @throws ExceptionCoupons if Company email or name exists
     */
    public void addCompany(Company company) throws ExceptionCoupons {
        if (companyRepo.existsByEmailOrName(company.getEmail(),company.getName()))
            throw new ExceptionCoupons("email or name exist already, try again");
        else
            companyRepo.save(company);
    }

    /**
     * The method receives a company object
     * if the company exists by id, and the name is not different from the db, the company update in the db.
     * @param company a company object
     * @throws ExceptionCoupons if the company not found by id, or if the name is different from the db
     */
    public void updateCompany(Company company) throws ExceptionCoupons{
        Company companyToCheck= getOneCompany(company.getId());
        if (companyToCheck.getName().equals(company.getName()))
            throw new ExceptionCoupons("The company name cannot be updated");
        companyRepo.save(companyToCheck);

    }

    /**
     * The method receives  company's id and checks if the company is exists in the 'companies' db,
     * if it exists the method over on all customers' coupons and deletes the coupons purchase of this company,
     *deletes the coupon, and then it deletes the company.
     * @param companyId -company's id
     * @throws ExceptionCoupons if the company is not exists in the db
    */
    public void deleteCompany(int companyId) throws ExceptionCoupons {
        if (companyRepo.existsById(companyId)) {
            List<Customer>customers = getAllCustomers();
            for (Customer cus : customers) {
                Set<Coupon> coupons= cus.getCoupons();
                coupons.removeIf(coup ->coup.getCompany().getId()==companyId );
                //cus.setCoupons(coupons);
                customerRepo.save(cus);
            }
            couponRepo.deleteByCompanyId(companyId);
            companyRepo.deleteById(companyId);
            return;
        }
        throw new ExceptionCoupons("The company doesn't exist");
    }

    public void deleteCouponPurchase(int customerId, int couponId) throws ExceptionCoupons {
        Customer customer = getOneCustomer(customerId);
            Set<Coupon> coupons= customer.getCoupons();
            coupons.removeIf(coup ->coup.getId()==couponId );
            customer.setCoupons(coupons);
            customerRepo.save(customer);
    }

    /**
     * The method returns all companies from the db
     * @return list of all the companies object
     */
    public  List<Company> getAllCompanies(){
        return companyRepo.findAll();
    }

    /**
     * The method receives company's id
     * and returns one company object
     * @param companyId company's id
     * @return a company object
     * @throws ExceptionCoupons if the company is not exists
     */
    public Company getOneCompany(int companyId) throws ExceptionCoupons {
        return companyRepo.findById(companyId).orElseThrow(()->new ExceptionCoupons("The company doesn't exist!"));
    }
    public void addCustomer(Customer customer) throws ExceptionCoupons {
        if (customerRepo.existsByEmail(customer.getEmail()))
            throw new ExceptionCoupons("This email exists");
        customerRepo.save(customer);
    }
    public void updateCustomer(Customer customer) throws ExceptionCoupons{
        Customer customerToCheck= getOneCustomer(customer.getId());
        customerRepo.save(customerToCheck);

    }
    public void deleteCustomer(int customerId) throws ExceptionCoupons {
        if (customerRepo.existsById(customerId)) {
            couponRepo.deleteCouponPurchaseByCustomerId(customerId);
            customerRepo.deleteById(customerId);
            return;
        }
        throw new ExceptionCoupons("The customer delete is failed");
    }
    public List<Customer>getAllCustomers(){
        return customerRepo.findAll();
    }
    public Customer getOneCustomer(int id) throws ExceptionCoupons {
        return customerRepo.findById(id).orElseThrow(()->new ExceptionCoupons("The customer not found!"));
    }

}
