package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder>{

    List<ExpenseNIncomeModel> searchedList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //

    public BalanceAdapter(List<ExpenseNIncomeModel> searchedList) {
        this.searchedList = searchedList;
    }

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_searchitem,parent,false);
        BalanceViewHolder balanceViewHolder=new BalanceViewHolder(view);
        balanceViewHolder.txtViewCategory = view.findViewById(R.id.txtViewCategory);
        balanceViewHolder.txtViewDate = view.findViewById(R.id.txtViewDate);
        balanceViewHolder.txtViewNote = view.findViewById(R.id.txtViewNote);
        balanceViewHolder.txtViewAmount = view.findViewById(R.id.txtViewAmount);

        return balanceViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int i) {
        holder.txtViewCategory.setText(searchedList.get(i).getCategory());
        holder.txtViewDate.setText(dateFormat.format(searchedList.get(i).getDate()));
        holder.txtViewNote.setText(searchedList.get(i).getNote());
        holder.txtViewAmount.setText(Double.toString(searchedList.get(i).getAmount()));

    }

    @Override
    public int getItemCount() {
        return searchedList.size();
    }

    public class BalanceViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewCategory;
        TextView txtViewDate ;
        TextView txtViewNote;
        TextView txtViewAmount;

        public BalanceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }




}
