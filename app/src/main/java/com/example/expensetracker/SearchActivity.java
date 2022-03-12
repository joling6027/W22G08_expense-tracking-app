package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView listViewResults;
    List<ExpenseNIncomeModel> populateList;
    String query;
    Toolbar toolBar2;
//    ArrayAdapter arrayAdapter;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolBar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolBar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(SearchActivity.this);
        listViewResults = findViewById(R.id.listViewResults);

        try {
            if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
                query = getIntent().getStringExtra(SearchManager.QUERY);
                Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();

//                Cursor data = (Cursor) databaseHelper.getSearchedData(query);
//                populateList = databaseHelper.getSearchedData(query); //item get from MainActivity

//                final SimpleCursorAdapter simpleCursorAdapter = databaseHelper.populateListViewFromDb();
//                listViewResults.setAdapter(simpleCursorAdapter);

//                if(data.getCount() == 0){
//                    Toast.makeText(SearchActivity.this, "The database was empty", Toast.LENGTH_SHORT).show();
//                }else{
//                    while(data.moveToNext()){
//                        populateList.add();
//                    }
//                }
//                arrayAdapter = new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,databaseHelper.getSearchedData(query));
                populateList = databaseHelper.getSearchedData(query);

                //for test only
                String showStr = populateList.get(0).getCategory() + ">> " + populateList.get(0).getAmount();
                Toast.makeText(SearchActivity.this, showStr, Toast.LENGTH_SHORT).show();

                SearchListAdapter searchListAdapter = new SearchListAdapter(populateList);
                listViewResults.setAdapter(searchListAdapter);
            }

        }catch (Exception ex){
            Toast.makeText(SearchActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }


    }
}