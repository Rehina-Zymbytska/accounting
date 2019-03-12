package com.company.entity;

import java.util.Calendar;
import java.util.Date;

public class Operation {

    private Category category;
    private double sum;
    private Date date;

    public Operation(int categoryId, double sum){
        this.category = Category.getCategoryById(categoryId);
        this.sum = (category == Category.SALARY || category == Category.REFUND)? sum : 0 - sum;
        Calendar cal = Calendar.getInstance();
        this.date = cal.getTime();
    }

    public Category getCategory() {
        return category;
    }

    public double getSum() {
        return sum;
    }

    public Date getDate() {
        return date;
    }
}
