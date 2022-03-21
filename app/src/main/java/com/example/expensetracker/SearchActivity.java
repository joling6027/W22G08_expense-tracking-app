package com.example.expensetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
                populateList = databaseHelper.getSearchedData(query);

                SearchListAdapter searchListAdapter = new SearchListAdapter(populateList);
                listViewResults.setAdapter(searchListAdapter);
            }

        }catch (Exception ex){
            Toast.makeText(SearchActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }


        listViewResults.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l)-> {
            ExpenseNIncomeModel clickedData = (ExpenseNIncomeModel) adapterView.getItemAtPosition(i);

            new AlertDialog.Builder(this)
                    .setTitle("Delete data")
                    .setMessage("Are you sure you want to delete this data?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseHelper.deleteData(clickedData);
                            Toast.makeText(SearchActivity.this, "data deleted", Toast.LENGTH_SHORT).show();

                            //refresh the list
                            try {
                                if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
                                    query = getIntent().getStringExtra(SearchManager.QUERY);
                                    Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();
                                    populateList = databaseHelper.getSearchedData(query);

                                    SearchListAdapter searchListAdapter = new SearchListAdapter(populateList);
                                    listViewResults.setAdapter(searchListAdapter);
                                }

                            }catch (Exception ex){
                                Toast.makeText(SearchActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                                ex.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        });
    }
}