package com.company.entity;

public enum Category {

    UNKNOWN(0, "Unknown"),
    SALARY(1, "Salary"),
    REFUND(2, "Refund"),
    FOOD(3, "Food"),
    EDUCATION(4, "Education"),
    HOUSE(5, "House"),
    TRANSPORT(6, "Transport"),
    HOBBY(7, "Hobby");

    public int id;
    public String name;

    Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category getCategoryById(int id){
        for (Category category : Category.values()){
            if (category.id == id) return category;
        }
        return UNKNOWN;
    }
}
