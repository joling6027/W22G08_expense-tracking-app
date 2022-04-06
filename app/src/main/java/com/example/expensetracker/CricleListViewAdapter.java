package com.example.expensetracker;

import android.view.View;

import java.util.List;

public abstract class CricleListViewAdapter {
    private CircleListView circleListView;
    private List<TransactionModel> categoryItemList;

    public CricleListViewAdapter(CircleListView circleListView) {
        this.circleListView = circleListView;
    }

    public int getCount() {
        return categoryItemList.size();
    }

    public abstract View getView(int position);


    public void setPosition(int position) {
        if (position > getCount() - 1) {
            position = getCount() - 1;
        } else if (position < 0) {
            position = 0;
        }
        circleListView.setPosition(position);
    }
}
