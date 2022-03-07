package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView listViewResults;
    List<ExpenseNIncomeModel> populateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listViewResults = findViewById(R.id.listViewResults);
        DatabaseHelper databaseHelper = new DatabaseHelper(SearchActivity.this);

        //get search data from MainActivity (bundle?)

        populateList = databaseHelper.getSearchedData(item); //item get from MainActivity
        SearchListAdapter searchListAdapter = new SearchListAdapter(populateList);
        listViewResults.setAdapter(searchListAdapter);

        //create a expenseNIncomeModel object and set bundle data in it (?)

    }
}