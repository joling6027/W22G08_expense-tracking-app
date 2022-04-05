package com.example.expensetracker;

import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder>{

    List<TransactionModel> categoryItemList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    OnItemClickListener onItemClickListener;
    DatabaseHelper databaseHelper;
    OnItemClickListener mSelected;

    public BalanceAdapter(List<TransactionModel> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public BalanceAdapter(List<TransactionModel> categoryItemList, OnItemClickListener onItemClickListener){
        this.categoryItemList = categoryItemList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_searchitem,parent,false);
        BalanceViewHolder balanceViewHolder=new BalanceViewHolder(view, mSelected);
        balanceViewHolder.txtViewCategory = view.findViewById(R.id.txtViewCategory);
        balanceViewHolder.txtViewDate = view.findViewById(R.id.txtViewDate);
        balanceViewHolder.txtViewNote = view.findViewById(R.id.txtViewNote);
        balanceViewHolder.txtViewAmount = view.findViewById(R.id.txtViewAmount);

        databaseHelper = new DatabaseHelper(view.getContext());

        return balanceViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int i) {
        holder.txtViewCategory.setText(categoryItemList.get(i).getCategory());
        holder.txtViewDate.setText(dateFormat.format(categoryItemList.get(i).getDate()));
        holder.txtViewNote.setText(categoryItemList.get(i).getNote());
        double amount = categoryItemList.get(i).getAmount();
        holder.txtViewAmount.setText(Double.toString(amount));
        if(amount > 0){
            holder.txtViewAmount.setTextColor(Color.BLUE);
        }else{
            holder.txtViewAmount.setTextColor(Color.RED);
        }
    }



    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public class BalanceViewHolder extends RecyclerView.ViewHolder{
        TextView txtViewCategory;
        TextView txtViewDate ;
        TextView txtViewNote;
        TextView txtViewAmount;

        public BalanceViewHolder(@NonNull View itemView, OnItemClickListener selected) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != 0 || position!= RecyclerView.NO_POSITION){
                        TransactionModel clickedData = categoryItemList.get(getAdapterPosition());
                        new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete data")
                        .setMessage("Are you sure you want to delete this data?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseHelper.deleteData(clickedData);
                                Toast.makeText(view.getContext(), "data deleted", Toast.LENGTH_SHORT).show();
                                //need to refresh the list
                                selected.onDeleteClick(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                    }
                }
            });
        }
    }

    interface OnItemClickListener{
        void onDeleteClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener selected) {
        mSelected = selected;
    }
}
