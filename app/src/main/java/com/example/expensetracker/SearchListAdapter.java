package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SearchListAdapter extends BaseAdapter {
    List<ExpenseNIncomeModel> transactionList;

    public SearchListAdapter(List<ExpenseNIncomeModel> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public int getCount() {
        return 0;
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
            txtViewCategory.setText(transactionList.get(i).getCategory());
            txtViewDate.setText(transactionList.get(i).getDate().toString());
            txtViewNote.setText(transactionList.get(i).getNote());
            txtViewAmount.setText((int) transactionList.get(i).getExpense());

        return view;
    }
}
