package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.expensetracker.ExpenseNIncomeModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_CATEGORY = "COLUMN_CATEGORY";
    public static final String COLUMN_NOTE = "COLUMN_NOTE";
    public static final String COLUMN_AMOUNT = "COLUMN_AMOUNT";
    public static final String COLUMN_DATE = "COLUMN_DATE";
    public static final String COLUMN_GROUP = "COLUMN_GROUP";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SQLiteDatabase db;
    Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "data1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDatabaseStat = "CREATE TABLE " + EXPENSE_TABLE +
                "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, " + COLUMN_NOTE + " TEXT, " + COLUMN_GROUP + " TEXT, " + COLUMN_AMOUNT + " REAL)";

        db.execSQL(createDatabaseStat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE);
        // Create tables again
        onCreate(db);
    }

    public boolean addData(ExpenseNIncomeModel expenseNIncomeModel) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_DATE, dateFormat.format(expenseNIncomeModel.getDate()));
            cv.put(COLUMN_CATEGORY, expenseNIncomeModel.getCategory());
            cv.put(COLUMN_AMOUNT, expenseNIncomeModel.getAmount());
            cv.put(COLUMN_NOTE, expenseNIncomeModel.getNote());
            cv.put(COLUMN_GROUP, expenseNIncomeModel.getGroup());
            return db.insert(EXPENSE_TABLE, null, cv) > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteData(ExpenseNIncomeModel expenseNIncomeModel) {

        String deleteQuery = "DELETE FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_ID + " = " + expenseNIncomeModel.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(deleteQuery, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public List<ExpenseNIncomeModel> getSearchedData(String item) {

        //create an empty arrayList
        List<ExpenseNIncomeModel> returnList = new ArrayList<>();
        //search query
        String queryString = "SELECT * FROM " + EXPENSE_TABLE + " WHERE UPPER(" + COLUMN_CATEGORY + ")= " + "UPPER('" + item + "')";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(0);
                    String dateStr = cursor.getString(1);
                    String cat = cursor.getString(2);
                    String note = cursor.getString(3);
                    String group = cursor.getString(4);
                    double amount = cursor.getDouble(5);
                    Date date = dateFormat.parse(dateStr);

                    Log.d("MyHelper", id + " " + dateStr + " " + cat + " " + note + " " + group + " " + amount);

                    ExpenseNIncomeModel expenseNIncomeModel1 = new ExpenseNIncomeModel(id, date, cat, note, group, amount);
                    returnList.add(expenseNIncomeModel1);

                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "date parse error", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (cursor.moveToNext());

        } else {
        }

        return returnList;
    }

    public List<ExpenseNIncomeModel> getAllData() {
        List<ExpenseNIncomeModel> returnList = new ArrayList<>();

        String statStr = "SELECT * FROM " + EXPENSE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(statStr, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(0);
                    String dateStr = cursor.getString(1);
                    String cat = cursor.getString(2);
                    String note = cursor.getString(3);
                    String group = cursor.getString(4);
                    double amount = cursor.getDouble(5);
                    Date date = dateFormat.parse(dateStr);
                    ExpenseNIncomeModel expenseNIncomeModel1 = new ExpenseNIncomeModel(id, date, cat, note, group, amount);
                    returnList.add(expenseNIncomeModel1);

                } catch (ParseException ps) {
                    ps.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return returnList;
    }

// getDateByDate
    public List<ExpenseNIncomeModel> getDataByDate(Date today) {

        //create an empty arrayList
        List<ExpenseNIncomeModel> returnList = new ArrayList<>();
        //search query
        String queryString = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_DATE + "= " + "'" + dateFormat.format(today) + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(0);
                    String dateStr = cursor.getString(1);
                    String cat = cursor.getString(2);
                    String note = cursor.getString(3);
                    String group = cursor.getString(4);
                    double amount = cursor.getDouble(5);
                    Date date = dateFormat.parse(dateStr);
                    Log.d("MyHelper", id + " " + dateStr + " " + cat + " " + note + " " + group + " " + amount);
                    ExpenseNIncomeModel expenseNIncomeModel1 = new ExpenseNIncomeModel(id, date, cat, note, group, amount);
                    returnList.add(expenseNIncomeModel1);

                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "date parse error", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (cursor.moveToNext());

        } else {
        }

        return returnList;
    }

    // getDateByYear

    public List<ExpenseNIncomeModel> getDataByYear(String year) {

        //create an empty arrayList
        List<ExpenseNIncomeModel> returnList = new ArrayList<>();
        //search query
        String queryString = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_DATE + " LIKE" + "'" + year + "%'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(0);
                    String dateStr = cursor.getString(1);
                    String cat = cursor.getString(2);
                    String note = cursor.getString(3);
                    String group = cursor.getString(4);
                    double amount = cursor.getDouble(5);
                    Date date = dateFormat.parse(dateStr);
                    ExpenseNIncomeModel expenseNIncomeModel1 = new ExpenseNIncomeModel(id, date, cat, note, group, amount);
                    returnList.add(expenseNIncomeModel1);

                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "date parse error", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (cursor.moveToNext());

        } else {
        }

        return returnList;
    }

    // getDateByYear

    public List<ExpenseNIncomeModel> getDataByMonth(String month) {

        //create an empty arrayList
        List<ExpenseNIncomeModel> returnList = new ArrayList<>();
        //search query
        String queryString = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_DATE + " LIKE" + "'_____" + month + "%'";
        Log.d("updateMonth", queryString);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(0);
                    String dateStr = cursor.getString(1);
                    String cat = cursor.getString(2);
                    String note = cursor.getString(3);
                    String group = cursor.getString(4);
                    double amount = cursor.getDouble(5);
                    Date date = dateFormat.parse(dateStr);
                    ExpenseNIncomeModel expenseNIncomeModel1 = new ExpenseNIncomeModel(id, date, cat, note, group, amount);
                    returnList.add(expenseNIncomeModel1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "date parse error", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (cursor.moveToNext());

        } else {
        }

        return returnList;
    }

    public ArrayList<PieModel> Pie(Date today) {
        ArrayList<PieModel> returnList = new ArrayList<>();
        String queryString = " SELECT COLUMN_CATEGORY, SUM(abs(COLUMN_AMOUNT)) FROM " + EXPENSE_TABLE
                + " WHERE " + COLUMN_DATE + " = " + "'" + dateFormat.format(today) + "'" + " AND "
                + COLUMN_CATEGORY + " NOT LIKE " + "'%salary%'" + " AND " + COLUMN_CATEGORY
                + " NOT LIKE " + "'deposit'" + " GROUP BY " + COLUMN_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    String cat = cursor.getString(0);
                    String amount = cursor.getString(1);
                    PieModel pieModel = new PieModel(cat, amount);
                    returnList.add(pieModel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return returnList;
    }

    public ArrayList<PieModel> PieMonth(String month) {
        ArrayList<PieModel> returnList = new ArrayList<>();
        String queryString = " SELECT COLUMN_CATEGORY, SUM(abs(COLUMN_AMOUNT)) FROM " + EXPENSE_TABLE
                + " WHERE " + COLUMN_DATE + " LIKE" + "'_____" + month + "%'" + " AND "
                + COLUMN_CATEGORY + " NOT LIKE " + "'%salary%'" + " AND " + COLUMN_CATEGORY
                + " NOT LIKE " + "'deposit'" + " GROUP BY " + COLUMN_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    String cat = cursor.getString(0);
                    String amount = cursor.getString(1);
                    PieModel pieModel = new PieModel(cat, amount);
                    returnList.add(pieModel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return returnList;
    }

    public ArrayList<PieModel> PieYear(String year) {
        ArrayList<PieModel> returnList = new ArrayList<>();
        String queryString = " SELECT COLUMN_CATEGORY, SUM(abs(COLUMN_AMOUNT)) FROM " + EXPENSE_TABLE
                + " WHERE " + COLUMN_DATE + " LIKE" + "'" + year + "%'" + " AND "
                + COLUMN_CATEGORY + " NOT LIKE " + "'%salary%'" + " AND " + COLUMN_CATEGORY
                + " NOT LIKE " + "'deposit'" + " GROUP BY " + COLUMN_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    String cat = cursor.getString(0);
                    String amount = cursor.getString(1);
                    PieModel pieModel = new PieModel(cat, amount);
                    returnList.add(pieModel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return returnList;
    }
}
