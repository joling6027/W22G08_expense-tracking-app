package com.example.expensetracker;


import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int mYear, mMonth, mDay;
    DrawerLayout dLayout;
    SearchView simpleSearchView;
    TextView textViewInterval, txtViewSummary, txtViewItem, categoryName;
    Month monthName;
    List<CategoryItem> categoryItemList = new ArrayList<>();
    List<ExpenseNIncomeModel> populateList= new ArrayList<>();
    DatabaseHelper databaseHelper;
    Date selectedDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Bundle bundle=new Bundle();
    String selectedCat=null;
    RelativeLayout relativeLayout;
    CircleListView circleListView;
    CategoryAdapterHome categoryAdapterHome;
    ImageView categoryImg;
    CategoryItem categoryItem;
    PieChart pieChart;
    ArrayList<PieEntry> pieCategories;
    ArrayList<PieModel> pieList = new ArrayList<>();
    int [] colors = {Color.parseColor("#DAF7A6"), Color.parseColor("#FFC300"),
            Color.parseColor("#FF5733"), Color.parseColor("#C70039"),
            Color.parseColor("#900C3F"), Color.parseColor("#581845"),
            Color.parseColor("#7393B3"), Color.parseColor("#5F9EA0"),
            Color.parseColor("#98FB98"), Color.parseColor("#FF69B4"),
            Color.parseColor("#5D3FD3"), Color.parseColor("#E97451")};

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
        if(getIntent().getSerializableExtra("date")==null){
            selectedDate=calendar.getTime();
        }else{
           selectedDate= (Date) getIntent().getSerializableExtra("date");
           calendar.setTime(selectedDate);
        }

        //set date
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);

        //textView Interval
        textViewInterval=findViewById(R.id.txtViewInterval);
        monthName = Month.of(mMonth+1);
        textViewInterval.setText(mDay+" "+monthName+" "+mYear);

        addData();
        databaseHelper = new DatabaseHelper(MainActivity.this);

        populateList = databaseHelper.getDataByDate(selectedDate);
        Log.d("myApp",calendar.getTime().toString());

        //circle list view
        init();

        //pie chart
        pieChart = findViewById(R.id.pie);
        pieList = databaseHelper.Pie(calendar.getTime());
        drawPie(pieList);

        //setTextViewSummary
        txtViewSummary=findViewById(R.id.txtViewSummary);
        getSummary(populateList,selectedCat);
        txtViewSummary.bringToFront();

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

    }//end of oncreate

    //initialize list view
    private void init() {
        relativeLayout = findViewById(R.id.relLayoutHome);
        circleListView = findViewById(R.id.circle_list_view);
        txtViewItem = findViewById(R.id.txtViewItem);
        categoryAdapterHome = new CategoryAdapterHome(circleListView) {
            @Override
            public View getView(int position) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.category_item, null);
                categoryName = view.findViewById(R.id.txtViewCategory);
                categoryImg = view.findViewById(R.id.imgViewCategory);
                categoryItem = categoryItemList.get(position);
                categoryName.setText(categoryItem.getCategoryName());
                categoryImg.setImageResource(categoryItem.getCategoryPic());
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        for(ExpenseNIncomeModel record:populateList){
                            if(record.getCategory().equals(categoryItemList.get(position).getCategoryName()))
                            {
                                txtViewItem.setText(String.format("%s \n%s", record.getCategory(), record.getAmount()));
                                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                                    txtViewSummary.bringToFront();
                                    txtViewItem.setVisibility(View.GONE);
                                } else{
                                    txtViewItem.setVisibility(View.VISIBLE);
                                    txtViewItem.bringToFront();
                                }
                            };
                        }
                        return true;
                    }
                });
                for(ExpenseNIncomeModel record:populateList){
                    if(record.getCategory().equals(categoryItemList.get(position).getCategoryName()) && record.getAmount() != 0)
                    {
                        txtViewItem.setText(String.format("%s \n%s", record.getCategory(), record.getAmount()));
                        categoryImg.setBackgroundResource(R.drawable.category_backgorund);
                    };
                }
                return view;
            }
            @Override
            public int getCount() {
                return categoryItemList.size();
            }
        };
        circleListView.setAdapter(categoryAdapterHome);
        categoryAdapterHome.setPosition(0);
    }

    //create pie chart
    private void drawPie(ArrayList<PieModel> pieList) {
        pieCategories = new ArrayList<>();
        for (int i = 0; i < pieList.size(); i++) {
            String category = pieList.get(i).getCategory();
            int amount = Integer.parseInt(pieList.get(i).getAmount());
            pieCategories.add(new PieEntry(amount, category));
        }
        PieDataSet pieDataSet = new PieDataSet(pieCategories, "Categories");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setHighlightEnabled(true);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setUsePercentValues(true);
        pieChart.clear();
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animate();
        pieChart.getLegend().setEnabled(false);
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
                if(itemId== R.id.month){
                    textViewInterval.setText(monthName.toString());
                    circleListView.setAdapter(categoryAdapterHome);
                    String month=mMonth>8?(mMonth+1)+"":"0"+(mMonth+1);
                    populateList=databaseHelper.getDataByMonth(month);
                    getSummary(populateList,selectedCat);
                    dLayout.closeDrawers();
                    pieList = databaseHelper.PieMonth(month);
                    init();
                    drawPie(pieList);
                }
                if(itemId== R.id.year){
                    textViewInterval.setText(mYear+" ");
                    circleListView.setAdapter(categoryAdapterHome);
                    populateList=databaseHelper.getDataByYear(mYear+"");
                    getSummary(populateList,selectedCat);
                    dLayout.closeDrawers();
                    pieList = databaseHelper.PieYear(String.valueOf(mYear));
                    init();
                    drawPie(pieList);
                }
                if(itemId== R.id.day){
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
                            circleListView.setAdapter(categoryAdapterHome);
                            populateList=databaseHelper.getDataByDate(selectedDate);
                            getSummary(populateList,selectedCat);
                            pieList = databaseHelper.Pie(selectedDate);
                            init();
                            drawPie(pieList);
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

        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        simpleSearchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();

        //add setOnItemClickListener so click on search icon will focus on searchView
        searchMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                simpleSearchView.setSearchableInfo(
                        searchManager.getSearchableInfo(getComponentName()));
                simpleSearchView.setIconified(false);
                simpleSearchView.setFocusable(true);
                return false;
            }
        });
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
        txtViewSummary.setText(Html.fromHtml(myExpense + "<br>" + myIncome));
    }

    //To add category details into a list
    private void addData() {
        categoryItemList.add(new CategoryItem("Vehicle", R.drawable.car));
        categoryItemList.add(new CategoryItem("Pet", R.drawable.pet));
        categoryItemList.add(new CategoryItem("Grocery", R.drawable.grocery));
        categoryItemList.add(new CategoryItem("Drink", R.drawable.drink));
        categoryItemList.add(new CategoryItem("Gift", R.drawable.gift));
        categoryItemList.add(new CategoryItem("Food", R.drawable.food));
        categoryItemList.add(new CategoryItem("Home", R.drawable.home));
        categoryItemList.add(new CategoryItem("Phone", R.drawable.phone));
        categoryItemList.add(new CategoryItem("Sports", R.drawable.sports));
        categoryItemList.add(new CategoryItem("Medical", R.drawable.medical));
        categoryItemList.add(new CategoryItem("Transit", R.drawable.transit));
        categoryItemList.add(new CategoryItem("Clothing", R.drawable.clothing));
        categoryItemList.add(new CategoryItem("Deposit", R.drawable.deposit));
        categoryItemList.add(new CategoryItem("Salary", R.drawable.salary));
    }

    //the end
}


