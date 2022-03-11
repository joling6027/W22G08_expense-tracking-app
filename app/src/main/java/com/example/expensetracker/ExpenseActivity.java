package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Toolbar toolBar;
    Button btnSelectDate;
    Button btnChooseCategory;
    Calendar calendar;
    TextView txtViewDate;
    TextView editTxtNotes;
    EditText editTxtDecimal;
    GridView gridViewCategory;
    List<CategoryItem> categoryItemList = new ArrayList<>();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        addData();

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnChooseCategory = findViewById(R.id.btnChooseCategory);
        gridViewCategory = findViewById(R.id.gridViewCategories);
        editTxtNotes = findViewById(R.id.txtViewNote);
        editTxtDecimal = findViewById(R.id.editTxtDecimal);

        btnSelectDate.setOnClickListener((View view) -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        btnChooseCategory.setOnClickListener((View view) -> {

            CategoryAdapter cAdapter = new CategoryAdapter(this, R.layout.category_item, categoryItemList);
            gridViewCategory.setAdapter(cAdapter);
            //set gridView OnItemClickListener

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

    private void addData() {
        categoryItemList.add(new CategoryItem(101, "Foods", R.drawable.ic_category));
        categoryItemList.add(new CategoryItem(102, "Transit", R.drawable.ic_category));
        categoryItemList.add(new CategoryItem(103, "Clothing", R.drawable.ic_category));
        categoryItemList.add(new CategoryItem(104, "Entertainment", R.drawable.ic_category));
        categoryItemList.add(new CategoryItem(105, "Travel", R.drawable.ic_category));
    }
}