package com.example.expensetracker;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class CategoryAdapterHome extends BaseAdapter {
    List<CategoryItem> categoryItemList = new ArrayList<>();
    List<ExpenseNIncomeModel> populateList;
    public List<ExpenseNIncomeModel> getPopulateList() {
        return populateList;
    }

    public void setPopulateList(List<ExpenseNIncomeModel> populateList) {
        this.populateList = populateList;
//        Log.d("updatePopulate",populateList.get(0).getCategory());
        notifyDataSetChanged();

    }



    public CategoryAdapterHome(List<CategoryItem> categoryItemList,List<ExpenseNIncomeModel> populateList) {
        this.categoryItemList = categoryItemList;
        this.populateList = populateList;
//        Log.d("initPopulate",populateList.get(0).getCategory());
    }

    @Override
    public int getCount() {
        return categoryItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        Log.d("MyApp",populateList.get(0).getCategory());
        if(view==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view=layoutInflater.inflate(R.layout.category_item, viewGroup, false);
        }
        TextView txtViewCategories = view.findViewById(R.id.txtViewCategory);
        ImageView imgViewCategories = view.findViewById(R.id.imgViewCategory);

        txtViewCategories.setText(categoryItemList.get(i).getCategoryName());
        imgViewCategories.setImageResource(categoryItemList.get(i).getCategoryPic());

        for(ExpenseNIncomeModel record:populateList){
            if(record.getCategory().equals(categoryItemList.get(i).getCategoryName()))
            {
                imgViewCategories.setBackgroundColor(Color.rgb(236, 144, 137));
            };
        }
        return view;
    }
}
