package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView listViewResults;
    List<ExpenseNIncomeModel> populateList;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            query = getIntent().getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }


        listViewResults = findViewById(R.id.listViewResults);
        DatabaseHelper databaseHelper = new DatabaseHelper(SearchActivity.this);

        //get search data from MainActivity (bundle?)

        populateList = databaseHelper.getSearchedData(query); //item get from MainActivity
        SearchListAdapter searchListAdapter = new SearchListAdapter(populateList);
        listViewResults.setAdapter(searchListAdapter);

        //create a expenseNIncomeModel object and set bundle data in it (?)

    }
}