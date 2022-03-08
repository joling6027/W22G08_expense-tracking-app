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
    private ArrayList<com.example.expensetracker.paymentMethodItem> mPaymentMethodList;
    private paymentMethodAdapter mAdapter;
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

        initList();
        Spinner spinnerPaymentMethods = findViewById(R.id.spinnerPaymentMethods);

        mAdapter = new paymentMethodAdapter(this, mPaymentMethodList);
        spinnerPaymentMethods.setAdapter(mAdapter);

        spinnerPaymentMethods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                com.example.expensetracker.paymentMethodItem clickedItem = (com.example.expensetracker.paymentMethodItem) adapterView.getItemAtPosition(i);
                String clickedPaymentMethods = clickedItem.getPaymentMethod();
                Toast.makeText(IncomeActivity.this, clickedPaymentMethods + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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

    private void initList() {
        mPaymentMethodList = new ArrayList<>();
        mPaymentMethodList.add(new com.example.expensetracker.paymentMethodItem("Cash", "CAD", R.drawable.ic_cash));
        mPaymentMethodList.add(new com.example.expensetracker.paymentMethodItem("Credit", "CAD", R.drawable.ic_credit_card));
        mPaymentMethodList.add(new com.example.expensetracker.paymentMethodItem("Debit", "CAD", R.drawable.ic_debit_card));
    }
}