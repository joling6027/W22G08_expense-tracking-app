package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class IncomeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Toolbar toolBar;
    Button btnSelectDate;
    Button btnChooseCategory;
    Calendar calendar;
    TextView txtViewDate;
    GridView gridViewCategory;
    int[] imgCategories = {R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category,
            R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category,
            R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category,
            R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category, R.drawable.ic_category};
    String[] txtCategories = {"Deposits", "Food", "Clothing", "Movie", "Travel", "Transit", "Salary", "Savings",
            "Deposits", "Food", "Clothing", "Movie", "Travel", "Transit", "Salary", "Savings",
            "Deposits", "Food", "Clothing", "Movie", "Travel", "Transit", "Salary", "Savings"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnChooseCategory = findViewById(R.id.btnChooseCategory);

        btnSelectDate.setOnClickListener((View view) -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        btnChooseCategory.setOnClickListener((View view) -> {
            gridViewCategory = findViewById(R.id.gridViewCategories);
            com.example.expensetracker.categoryAdapter cAdapter = new com.example.expensetracker.categoryAdapter(txtCategories, imgCategories, this);
            gridViewCategory.setAdapter(cAdapter);
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        txtViewDate = (TextView) findViewById(R.id.txtViewDate);
        txtViewDate.setText(currentDate);
    }
}