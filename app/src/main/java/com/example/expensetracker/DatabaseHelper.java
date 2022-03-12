package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_CATEGORY = "COLUMN_CATEGORY";
    public static final String COLUMN_NOTE = "COLUMN_NOTE";
    public static final String COLUMN_AMOUNT = "COLUMN_AMOUNT";
    public static final String COLUMN_DATE = "COLUMN_DATE";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SQLiteDatabase db;
    Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDatabaseStat = "CREATE TABLE " + EXPENSE_TABLE +
                "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, " + COLUMN_NOTE + " TEXT, " + COLUMN_AMOUNT + " REAL)";

        db.execSQL(createDatabaseStat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP IF TABLE EXISTS " + EXPENSE_TABLE);
    }

    public boolean addData(ExpenseNIncomeModel expenseNIncomeModel){
        try {
            SQLiteDatabase db =this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_DATE, dateFormat.format(expenseNIncomeModel.getDate()));
            cv.put(COLUMN_CATEGORY,expenseNIncomeModel.getCategory());
            cv.put(COLUMN_AMOUNT,expenseNIncomeModel.getAmount());
            cv.put(COLUMN_NOTE,expenseNIncomeModel.getNote());
            return db.insert(EXPENSE_TABLE, null, cv) > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteData(ExpenseNIncomeModel expenseNIncomeModel){

        String deleteQuery = "DELETE FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_ID + " = " + expenseNIncomeModel.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(deleteQuery, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public List<ExpenseNIncomeModel> getSearchedData(String item){

        //create an empty arrayList
        List<ExpenseNIncomeModel> returnList = new ArrayList<>();
        //search query
        String queryString = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_CATEGORY + "= " + "'" + item + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                try {
                int id = cursor.getInt(0);
                String dateStr = cursor.getString(1);
                String cat = cursor.getString(2);
                String note = cursor.getString(3);
                double amount = cursor.getDouble(4);
                Date date = dateFormat.parse(dateStr);
                ExpenseNIncomeModel expenseNIncomeModel1 = new ExpenseNIncomeModel(id,date,cat,note,amount);
                returnList.add(expenseNIncomeModel1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "parse error", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }while (cursor.moveToNext());

        }else { }

        return returnList;
    }
    public SimpleCursorAdapter populateListViewFromDb(){
        String columns[] = {DatabaseHelper.COLUMN_CATEGORY,DatabaseHelper.COLUMN_DATE,DatabaseHelper.COLUMN_NOTE,DatabaseHelper.COLUMN_AMOUNT};
        Cursor cursor = db.query(DatabaseHelper.EXPENSE_TABLE, columns,null,null,null,null,null);
        int[] toViewIds = new int[]{R.id.txtViewCategory,R.id.txtViewDate,R.id.txtViewNote,R.id.txtViewAmount};
        String[] fromFieldNames = new String[]{DatabaseHelper.COLUMN_CATEGORY,DatabaseHelper.COLUMN_DATE,DatabaseHelper.COLUMN_NOTE,DatabaseHelper.COLUMN_AMOUNT};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                context,
                R.layout.layout_searchitem,
                cursor,
                fromFieldNames,
                toViewIds
        );
        return simpleCursorAdapter;
    }


}
