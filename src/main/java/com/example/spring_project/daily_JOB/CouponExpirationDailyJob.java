package com.example.spring_project.daily_JOB;

import com.example.spring_project.beans.Coupon;
import com.example.spring_project.exception.ExceptionCoupons;
import com.example.spring_project.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
@Service
public class CouponExpirationDailyJob implements Runnable {
    private CouponRepository couponRepo;
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
                   couponRepo.deleteCouponPurchaseByCouponId(coupon.getId());
                   // couponsDao.deleteCoupon(coupon.getId());
                    System.out.println("delete success");
                }
            }
        } catch (ExceptionCoupons e) {
            System.out.println(e.getMessage());;
        }
    }

}