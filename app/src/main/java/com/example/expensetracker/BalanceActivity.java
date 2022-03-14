package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class BalanceActivity extends AppCompatActivity {

    ListView listViewResults;
    Toolbar toolBarBalance;
    //    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        toolBarBalance = findViewById(R.id.toolbarBalance);
        setSupportActionBar(toolBarBalance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listViewResults = findViewById(R.id.listViewBalance);

        try {
                ArrayList<ExpenseNIncomeModel> populateList= (ArrayList<ExpenseNIncomeModel>) getIntent().getExtras().getSerializable("populateList");
                SearchListAdapter searchListAdapter = new SearchListAdapter(populateList);
                listViewResults.setAdapter(searchListAdapter);
        }catch (Exception ex){
            Toast.makeText(BalanceActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
}




