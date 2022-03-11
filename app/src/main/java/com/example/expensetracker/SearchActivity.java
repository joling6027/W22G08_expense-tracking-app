package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView listViewResults;
    List<ExpenseNIncomeModel> populateList;
    String query;
    Toolbar toolBar2;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolBar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolBar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewResults = findViewById(R.id.listViewResults);
        try {
            if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
                query = getIntent().getStringExtra(SearchManager.QUERY);
                Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();

                //use the query to search your data somehow
                DatabaseHelper databaseHelper = new DatabaseHelper(SearchActivity.this);
                populateList = databaseHelper.getSearchedData(query); //item get from MainActivity

                //can I use searchListAdapter here?
                arrayAdapter = new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,databaseHelper.getSearchedData(query));
                listViewResults.setAdapter(arrayAdapter);
            }

        }catch (Exception ex){
            Toast.makeText(SearchActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }

//        SearchListAdapter searchListAdapter = new SearchListAdapter(populateList);
//        listViewResults.setAdapter(searchListAdapter);


//create a expenseNIncomeModel object and set bundle data in it (?)


    }
}