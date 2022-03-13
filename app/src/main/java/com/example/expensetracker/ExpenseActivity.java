package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import java.time.DateTimeException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //Variables
    List<CategoryItem> categoryItemList = new ArrayList<>();
    Button btnSelectDate, btnChooseCategory;
    EditText editTxtAmount, editTxtNotes;
    GridView gridViewCategory;
    TextView txtViewDate;
    Calendar calendar;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        //Add categories to gridView
        addData();

        //customized toolbar
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get edit text by id
        editTxtAmount = findViewById(R.id.editTxtDecimal);
        editTxtNotes = findViewById(R.id.editTxtNotes);

        //button to select date
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener((View view) -> {
            //display calendar for date selection
            showCalendar();
        });

        //button to choose category
        btnChooseCategory = findViewById(R.id.btnChooseCategory);
        btnChooseCategory.setOnClickListener((View view) -> {
            //display gridView
            gridViewCategory = findViewById(R.id.gridViewCategories);
            CategoryAdapter cAdapter = new CategoryAdapter(this, R.layout.category_item, categoryItemList);
            gridViewCategory.setAdapter(cAdapter);
            //when a category is chosen
            gridViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //get database
                    DatabaseHelper DB = new DatabaseHelper(getApplicationContext());
                    //create a object
                    ExpenseNIncomeModel expenseNIncomeModel = new ExpenseNIncomeModel();
                    //try and catch exception for inputs
                    try{
                        //store inputs into object
                        expenseNIncomeModel.setAmount(Double.parseDouble(editTxtAmount.getText().toString()));
                        expenseNIncomeModel.setNote(editTxtNotes.getText().toString());
                        expenseNIncomeModel.setDate(calendar.getTime());
                        expenseNIncomeModel.setCategory(categoryItemList.get(i).categoryName);
                        expenseNIncomeModel.setGroup("expense");
                        //catch exception for when amount is empty
                    } catch (NumberFormatException ex) {
                        //Display when amount if empty
                        Toast.makeText(ExpenseActivity.this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException ex) {
                        Toast.makeText(ExpenseActivity.this, "Date is not selected", Toast.LENGTH_SHORT).show();
                    }
                    //check if inputs are stored successfully
                    Boolean success = DB.addData(expenseNIncomeModel);
                    if (success == true) {
                        //display entry stored successfully and move back to home page
                        Toast.makeText(ExpenseActivity.this, "Entry Inserted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ExpenseActivity.this, MainActivity.class));
                    } else {
                        //display entry stored unsuccessfully and stay in expense entry page
                        Toast.makeText(ExpenseActivity.this, "Entry Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    //To add category details into a list
    private void addData() {
        categoryItemList.add(new CategoryItem("Vehicle", R.drawable.car));
        categoryItemList.add(new CategoryItem("Pet", R.drawable.cat));
        categoryItemList.add(new CategoryItem("Grocery", R.drawable.diet));
        categoryItemList.add(new CategoryItem("Drink", R.drawable.drink));
        categoryItemList.add(new CategoryItem("Gift", R.drawable.gift));
        categoryItemList.add(new CategoryItem("Food", R.drawable.fork));
        categoryItemList.add(new CategoryItem("Home", R.drawable.home));
        categoryItemList.add(new CategoryItem("Phone", R.drawable.phone));
        categoryItemList.add(new CategoryItem("Sports", R.drawable.soccerplayer));
        categoryItemList.add(new CategoryItem("Thermometer", R.drawable.thermometer));
        categoryItemList.add(new CategoryItem("Transit", R.drawable.train));
        categoryItemList.add(new CategoryItem("Clothing", R.drawable.tshirt));
    }

    //get calendar
    public void showCalendar() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    //display chosen date in textView
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        txtViewDate = (TextView) findViewById(R.id.txtViewDate);
        txtViewDate.setText(currentDate);
    }
}