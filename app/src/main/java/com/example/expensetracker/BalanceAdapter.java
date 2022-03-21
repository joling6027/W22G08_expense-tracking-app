package com.example.expensetracker;

import android.content.DialogInterface;
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

    List<ExpenseNIncomeModel> searchedList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    OnItemClickListener onItemClickListener;
    DatabaseHelper databaseHelper;
    //

    public BalanceAdapter(List<ExpenseNIncomeModel> searchedList) {
        this.searchedList = searchedList;
    }

    public BalanceAdapter(List<ExpenseNIncomeModel> searchedList,OnItemClickListener onItemClickListener){
        this.searchedList = searchedList;
        this.onItemClickListener = onItemClickListener;
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

        databaseHelper = new DatabaseHelper(view.getContext());

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

    public class BalanceViewHolder extends RecyclerView.ViewHolder{
        TextView txtViewCategory;
        TextView txtViewDate ;
        TextView txtViewNote;
        TextView txtViewAmount;

        public BalanceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != 0 || position!= RecyclerView.NO_POSITION){
                        ExpenseNIncomeModel clickedData = searchedList.get(getAdapterPosition());
                        new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete data")
                        .setMessage("Are you sure you want to delete this data?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseHelper.deleteData(clickedData);
                                Toast.makeText(view.getContext(), "data deleted", Toast.LENGTH_SHORT).show();
                                //need to refresh the list
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
        void onItemClick(int i);
    }


}
