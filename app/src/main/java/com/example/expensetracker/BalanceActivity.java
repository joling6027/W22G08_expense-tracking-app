package com.example.expensetracker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class BalanceActivity extends AppCompatActivity {

    RecyclerView recyclerViewResults;
    Toolbar toolBarBalance;
    //    ArrayAdapter arrayAdapter;
    ArrayList<TransactionModel> categoryItemList;
    BalanceAdapter balanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        toolBarBalance = findViewById(R.id.toolbarBalance);
        setSupportActionBar(toolBarBalance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        recyclerViewResults = findViewById(R.id.recyclerViewBalance);

        try {
                categoryItemList = (ArrayList<TransactionModel>) getIntent().getExtras().getSerializable("populateList");
                balanceAdapter = new BalanceAdapter(categoryItemList);
                recyclerViewResults.setLayoutManager(new LinearLayoutManager(this));
                recyclerViewResults.setAdapter(balanceAdapter);
                balanceAdapter.setOnItemClickListener(new BalanceAdapter.OnItemClickListener() {
                    @Override
                    public void onDeleteClick(int i) {
                        deleteItem(i);
                    }
                });
        }catch (Exception ex){
            Toast.makeText(BalanceActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public void deleteItem(int position) {
        categoryItemList.remove(position);
        balanceAdapter.notifyItemRemoved(position);
    }
}




