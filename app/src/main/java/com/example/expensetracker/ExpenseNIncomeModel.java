package com.example.expensetracker;

import java.util.Date;

public class ExpenseNIncomeModel {

    private int id;
    private Date date;
    private String category;
    private String note;
    private double expense;

    public ExpenseNIncomeModel(int id, Date date, String category, String note, double expense) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.note = note;
        this.expense = expense;
    }

    public ExpenseNIncomeModel() {
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

    public double getExpense() {
        return expense;
    }
    public void setExpense(double expense) {
        this.expense = expense;
    }
}
