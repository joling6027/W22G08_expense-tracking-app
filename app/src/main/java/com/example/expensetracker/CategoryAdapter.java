package com.example.expensetracker;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

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
import java.util.List;

public class CategoryAdapter extends ArrayAdapter {
    List<TransactionModel> categoryItemList = new ArrayList<>();
    int category_item_id;

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<TransactionModel> objects) {
        super(context, resource, objects);
        categoryItemList = objects;
        category_item_id = resource;
    }

    @Override
    public int getCount() {
        return categoryItemList.size();
    }

    @Override
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = layoutInflater.inflate(category_item_id, null);
        }
        TextView txtViewCategories = view.findViewById(R.id.txtViewCategory);
        ImageView imgViewCategories = view.findViewById(R.id.imgViewCategory);

        txtViewCategories.setText(categoryItemList.get(i).getCategory());
        imgViewCategories.setImageResource(categoryItemList.get(i).getPic());

        return view;
    }
}
