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
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;



import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] inteval = {"day","month", "year", "pick"};
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int mYear, mMonth, mDay;
    DrawerLayout dLayout;
    SearchView simpleSearchView;
    TextView textViewInterval;
    Month monthName;
    GridView gridViewCategoryHome;
    List<CategoryItem> categoryItemList = new ArrayList<>();
    List<ExpenseNIncomeModel> populateList= new ArrayList<>();
    DatabaseHelper databaseHelper;
    Date selectedDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CategoryAdapterHome cAdapter;
    double expense;
    double income;
    TextView textViewSummary;
    Bundle bundle=new Bundle();
    String selectedCat=null;

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

        //add gridView
        selectedDate=calendar.getTime();
        addData();
        databaseHelper = new DatabaseHelper(MainActivity.this);

        populateList = databaseHelper.getDataByDate(selectedDate);
        Log.d("myApp",calendar.getTime().toString());

        gridViewCategoryHome=findViewById(R.id.girdVeiwCategoryHome);
        cAdapter = new CategoryAdapterHome(categoryItemList,populateList);
        gridViewCategoryHome.setAdapter(cAdapter);


        //setTextViewSummary
        textViewSummary=findViewById(R.id.txtViewSummary);
        getSummary(populateList,selectedCat);



        //balance button
        Button computeBalance=findViewById(R.id.btnBalance);
        computeBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,BalanceActivity.class);
                bundle.putSerializable("populateList", (Serializable) populateList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //



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


        //imageViewOnClick
        gridViewCategoryHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCat=categoryItemList.get(i).getCategoryName();
                getSummary(populateList,selectedCat);
            }
        });

        //textVeiwSummary
        textViewSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSummary(populateList,null);
            }
        });


    }//end of oncreate






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
                    gridViewCategoryHome.setAdapter(cAdapter);
                    String month=mMonth>8?(mMonth+1)+"":"0"+(mMonth+1);
                    populateList=databaseHelper.getDataByMonth(month);
                    cAdapter.setPopulateList(populateList);
                    cAdapter.notifyDataSetChanged();
                    getSummary(populateList,selectedCat);
                    dLayout.closeDrawers();
                }
                if(itemId==R.id.year){
                    textViewInterval.setText(mYear+" ");
                    gridViewCategoryHome.setAdapter(cAdapter);
                    populateList=databaseHelper.getDataByYear(mYear+"");
                    cAdapter.setPopulateList(populateList);
                    cAdapter.notifyDataSetChanged();
                    getSummary(populateList,selectedCat);
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
                            try {
                                selectedDate=dateFormat.parse(mYear+"-"+(mMonth+1)+"-"+mDay);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.d("selectedTime",selectedDate.toString());
                            gridViewCategoryHome.setAdapter(cAdapter);
                            populateList=databaseHelper.getDataByDate(selectedDate);
                            cAdapter.setPopulateList(populateList);
                            getSummary(populateList,selectedCat);
                            cAdapter.notifyDataSetChanged();
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

    //update textViewSummary
    private void getSummary(List<ExpenseNIncomeModel> populateList,String selectedCat) {
        double expense = 0;
        double income = 0;
        if (selectedCat == null) {
            for (ExpenseNIncomeModel record : populateList) {
                if (record.getGroup().equals("expense")) {
                    expense += record.getAmount();
                } else if (record.getGroup().equals("income")) {
                    income += record.getAmount();
                }
            }
        } else {
            for (ExpenseNIncomeModel record : populateList) {
                if (record.getGroup().equals("expense") && record.getCategory().equals(selectedCat)) {
                    expense += record.getAmount();
                } else if (record.getGroup().equals("income")) {
                    income += record.getAmount();
                }
            }
        }


            Log.d("expenseIncome", expense + " " + income);
            String myExpense = "<font color=#800000>" + expense + "</font>";
            String myIncome = "<font color=#000080>" + income + "</font>";
            textViewSummary.setText(Html.fromHtml(myExpense + "<br>" + myIncome));


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





    //the end
}


