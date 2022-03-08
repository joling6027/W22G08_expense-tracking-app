package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class paymentMethodAdapter extends ArrayAdapter<com.example.expensetracker.paymentMethodItem> {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    public paymentMethodAdapter(Context context, ArrayList<com.example.expensetracker.paymentMethodItem> paymentMethodList) {
        super(context, 0,paymentMethodList);

    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.payment_method_spinner, parent, false
            );
        }

        ImageView imageViewPaymentMethod = convertView.findViewById(R.id.imgViewPaymentMethod);
        TextView txtViewCurrency = convertView.findViewById(R.id.txtViewCurrency);
        TextView txtViewPaymentMethod = convertView.findViewById(R.id.txtViewPaymentMethod);

        com.example.expensetracker.paymentMethodItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewPaymentMethod.setImageResource(currentItem.getPaymentMethodImage());
            txtViewCurrency.setText((currentItem.getCurrency()));
            txtViewPaymentMethod.setText((currentItem.getPaymentMethod()));
        }

        return convertView;
    }
}
