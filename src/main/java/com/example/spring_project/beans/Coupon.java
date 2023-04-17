package com.example.spring_project.beans;

import com.example.spring_project.exception.ExceptionCoupons;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

import lombok.ToString;
import org.hibernate.sql.Delete;

@Entity
@Table(name = "coupons")
@Data
@ToString
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    @ManyToOne()
    private Company company;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title, description;
    private Date startDate, endDate;
    private int amount;
    private double price;
    private String image;
    @ManyToMany(mappedBy = "coupons",fetch = FetchType.EAGER)
    private Set<Customer> customers;

    public Coupon() {
    }

    public Coupon(Company company, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) throws ExceptionCoupons {
        this.company = company;
        this.category = category;
        this.title = title;
        this.description = description;
        setStartDate(startDate);
        setEndDate(endDate);
        setAmount(amount);
        setPrice(price);
        this.image = image;
    }

    public Coupon(int id, Company company, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) throws ExceptionCoupons {
        this.id = id;
        this.company = company;
        this.category = category;
        this.title = title;
        this.description = description;
        setStartDate(startDate);
        setEndDate(endDate);
        setAmount(amount);
        setPrice(price);
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) throws ExceptionCoupons {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new Date(millis);
        if (endDate.before(date) && endDate.before(getStartDate()))
            throw new ExceptionCoupons("Invalid date");
        else
            this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) throws ExceptionCoupons {
        if (amount>=1)
            this.amount = amount;
        else throw new ExceptionCoupons("Amount must be above one");
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws ExceptionCoupons {
        if (price>=1.0)
            this.price = price;
        else
            throw new ExceptionCoupons("Invalid price!");
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", company=" + company.getId() +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
