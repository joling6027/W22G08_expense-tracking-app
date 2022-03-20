package com.example.expensetracker;

public class PieModel {
    String category;
    String amount;

    public PieModel(String category, String amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public String getAmount() {
        return amount;
    }
}
