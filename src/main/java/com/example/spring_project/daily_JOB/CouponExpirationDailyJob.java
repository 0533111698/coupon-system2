package com.example.spring_project.daily_JOB;

import com.example.spring_project.beans.Coupon;
import com.example.spring_project.beans.Customer;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.repository.CouponRepository;
import com.example.spring_project.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Service
public class CouponExpirationDailyJob implements Runnable {
    private CouponRepository couponRepo;
    private CustomerRepository customerRepo;
    private boolean quit;
    private Thread thread = new Thread(this, "dailyJob");

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public CouponExpirationDailyJob() {
    }

    public CouponExpirationDailyJob(boolean quit) {
        this.quit = quit;
    }

    /**
     * The method is Override to method 'run' and checks if this thread is not quit
     *if not, the check expired have called
     */
    @Override
    public void run() {
        while (!this.quit) {
            try {
                checkExpired();
                Thread.sleep(1000 * 60 * 60 * 24);
            } catch (InterruptedException ignored) {
            }

        }
    }

    /**
     * the method start the thread
     */
    public void start() {
        this.thread.start();
        quit = false;
    }

    /**
     * the method changes the quit to true and wake up the thread to close the thread
     */
    public void stop() {
        quit = true;
        thread.interrupt();
        System.out.println("the program end");

    }

    /**
     * checks for all coupons if their dates are before today
     * if yes the method delete the purchase of coupon from 'coupons' db
     * by the method 'deleteCouponPurchaseByCouponId' from the dao
     * and delete coupon from 'coupons' db by the method 'deleteCoupon' from the dao
     */
    public void checkExpired() {
        try {
            long millis = System.currentTimeMillis();
            java.sql.Date date = new Date(millis);
            List<Coupon> coupons = couponRepo.findAll();
            for (Coupon coupon : coupons) {
                if (coupon.getEndDate().before(date)) {
                    List<Customer> customers = customerRepo.findAll();
                    for (Customer cus : customers) {
                        Set<Coupon> cusCoupons = cus.getCoupons();
                        coupons.removeIf(coup -> coup.getId() == coupon.getId());
                        cus.setCoupons(cusCoupons);
                        customerRepo.save(cus);
                    }
                   couponRepo.deleteById(coupon.getId());
                   System.out.println("delete success");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}