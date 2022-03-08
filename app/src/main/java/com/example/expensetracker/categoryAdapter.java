package com.example.expensetracker;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class categoryAdapter extends BaseAdapter {
    private String[] txtCategories;
    private int[] imgCategories;
    private Context context;
    private LayoutInflater layoutInflater;

    public categoryAdapter(String[] txtCategories, int[] imgCategories, Context context) {
        this.txtCategories = txtCategories;
        this.imgCategories = imgCategories;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imgCategories.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.category_item, viewGroup, false);
        }
        TextView txtViewCategories = view.findViewById(R.id.txtViewCategory);
        ImageView imgViewCategories = view.findViewById(R.id.imgViewCategory);

        txtViewCategories.setText(txtCategories[i]);
        imgViewCategories.setImageResource(imgCategories[i]);

        return view;
    }
}
