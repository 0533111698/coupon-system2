package com.example.spring_project.test;

import com.example.spring_project.daily_JOB.CouponExpirationDailyJob;
import com.example.spring_project.exception.ExceptionCoupons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class TestAll {
    private ApplicationContext ctx;

    public TestAll(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public void testAll(){
        TestAdmin testAdmin=ctx.getBean(TestAdmin.class);
        TestCompany testCompany=ctx.getBean(TestCompany.class);
        TestCustomer testCustomer=ctx.getBean(TestCustomer.class);
        try {
            CouponExpirationDailyJob job = ctx.getBean(CouponExpirationDailyJob.class);
            Thread t1 = new Thread(job);
            t1.start();
            //  testAdmin.runAllAdminFacadeTest();
        //  testCompany.runAllCompanyFacadeTest();
             testCustomer.runAllCustomerFacadeTest();
          job.stop();
          t1.interrupt();
        } catch (ExceptionCoupons e) {
            System.out.println(e.getMessage());
        }

    }
}
