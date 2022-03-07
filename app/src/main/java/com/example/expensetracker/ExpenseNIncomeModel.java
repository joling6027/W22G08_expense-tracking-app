package com.example.expensetracker;

import java.util.Date;

public class ExpenseNIncomeModel {

    private int id;
    private Date date;
    private String category;
    private String note;
    private double amount;

    public ExpenseNIncomeModel(int id, Date date, String category, String note, double amount) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.note = note;
        this.amount = amount;
    }

    public ExpenseNIncomeModel() {
    }

    @Override
    public String toString() {
        return "ExpenseNIncomeModel{" +
                "id=" + id +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", note='" + note + '\'' +
                ", amount=" + amount +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
