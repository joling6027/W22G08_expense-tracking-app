package com.example.expensetracker;



import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;

import java.time.Month;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String[] inteval = {"day","month", "year", "pick"};
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int mYear, mMonth, mDay;
    DrawerLayout dLayout;
    SearchView simpleSearchView;
    TextView textViewInterval;
    Month monthName;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // implement setNavigationOnClickListener event
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });
        setNavigationDrawer(); // call method



        //calendar picker
        calendar=Calendar.getInstance();
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);


        //textView Interval
        textViewInterval=findViewById(R.id.txtViewInterval);
        monthName = Month.of(mMonth+1);
        textViewInterval.setText(mDay+" "+monthName+" "+mYear);



        //balance button
        Button computeBalance=findViewById(R.id.btnBalance);
        computeBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,BalanceActivity.class));
            }
        });



        // input expense
        ImageView imageViewExpense=findViewById(R.id.imageViewExpense);

        imageViewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ExpenseActivity.class));
            }
        });


        //input income
        ImageView imageViewIncome=findViewById(R.id.imageViewIncome);
        imageViewIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,IncomeActivity.class));
            }
        });


    }


    //NavigationDrawer
    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        // initiate a Navigation View
        // implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.month){
                    textViewInterval.setText(monthName.toString());
                    dLayout.closeDrawers();
                }
                if(itemId==R.id.year){
                    textViewInterval.setText(mYear+" ");
                    dLayout.closeDrawers();
                }
                if(itemId==R.id.day){
                    datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                mDay=day;
                                mMonth=month;
                                mYear=year;
                                monthName = Month.of(mMonth+1);
                                textViewInterval.setText(mDay+" "+monthName+" "+mYear);
                        }
                    },mYear,mMonth,mDay);
                    datePickerDialog.show();


                    dLayout.closeDrawers();
                }

                return false;
            }
        });

    }


    //inflate menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        simpleSearchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        simpleSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }



    //the end
}


