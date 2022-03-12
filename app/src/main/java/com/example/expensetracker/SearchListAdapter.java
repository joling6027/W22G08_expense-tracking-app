package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SearchListAdapter extends BaseAdapter {
    List<ExpenseNIncomeModel> searchedList;

    public SearchListAdapter(List<ExpenseNIncomeModel> searchedList) {
        this.searchedList = searchedList;
    }

    @Override
    public int getCount() {
        return searchedList.size();
    }

    @Override
    public ExpenseNIncomeModel getItem(int i) {
        return searchedList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return searchedList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.layout_searchitem,viewGroup,false);
        }
//        ImageView imgViewCat = view.findViewById(R.id.imgViewCat);
        TextView txtViewCategory = view.findViewById(R.id.txtViewCategory);
        TextView txtViewDate = view.findViewById(R.id.txtViewDate);
        TextView txtViewNote = view.findViewById(R.id.txtViewNote);
        TextView txtViewAmount = view.findViewById(R.id.txtViewAmount);

//        imgViewCat.setImageResource();
            txtViewCategory.setText(searchedList.get(i).getCategory());
            txtViewDate.setText(searchedList.get(i).getDate().toString());
            txtViewNote.setText(searchedList.get(i).getNote());
            txtViewAmount.setText(Double.toString(searchedList.get(i).getAmount()));

        return view;
    }
}
