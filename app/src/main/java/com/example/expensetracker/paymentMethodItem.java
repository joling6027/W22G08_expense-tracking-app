package com.example.expensetracker;

public class paymentMethodItem {
    private String mPaymentMethod;
    private String mCurrency;
    private int mPaymentMethodImage;

    public paymentMethodItem(String paymentMethod, String currency, int paymentMethodImage) {
        mPaymentMethod = paymentMethod;
        mCurrency = currency;
        mPaymentMethodImage = paymentMethodImage;
    }

    public String getPaymentMethod() {
        return mPaymentMethod;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public int getPaymentMethodImage() {
        return mPaymentMethodImage;
    }
}
