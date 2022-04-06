package com.example.expensetracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CircleListView extends ViewGroup {
    private final double itemIntervalAngle = 25.7; //360 degree ÷ 14 items
    private int circleRadius, circleCenterX, circleCenterY;
    private double angle; //Each item angle
    private Bitmap circleBitmap; //Bitmap to store each rectangle object for items
    private Rect itemImg; //A rectangle for item image
    private Rect itemName; //A rectangle for item name
    private Paint paint; //Painting brush
    private CricleListViewAdapter adapterHome = new CricleListViewAdapter(this) {
        @Override
        public View getView(int position) {
            return new View(getContext());
        }
    };

    public CircleListView(Context context) {
        super(context);
        //set to false in order to get onDraw called
        setWillNotDraw(false);
        //create a new paint with default settings
        paint = new Paint();
    }

    public CircleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //set to false in order to get onDraw called
        setWillNotDraw(false);
        //create a new paint with default settings
        paint = new Paint();
    }

    //customize the measures of the view
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //ask all of the children in this view to measure themselves
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    //draw areas to place items
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (circleBitmap != null) {
            //if there is no area to store item image
            if (itemImg == null) {
                //create a rectangle area for item image
                itemImg = new Rect();
            }
            //gets the x-axis position of the left side of the rectangle
            itemImg.left = 0;
            //get the y-axis position of the right side of the rectangle
            itemImg.top = 0;
            //gets item width as the x-axis value of the right side
            itemImg.right = circleBitmap.getWidth();
            //gets item height as the y-axis value of the bottom side
            itemImg.bottom = circleBitmap.getHeight();

            //if there is no area to store item name
            if (itemName == null) {
                //create a rectangle area for item name
                itemName = new Rect();
            }

            //gets the x-axis position of the left side of the rectangle
            itemName.left = circleCenterX - circleBitmap.getWidth() / 4;
            //get the y-axis position of the right side of the rectangle
            itemName.top = circleCenterY - circleBitmap.getHeight() / 4;
            //gets item width as the x-axis value of the right side
            itemName.right = circleCenterX + circleBitmap.getWidth() / 4;
            //gets item height as the y-axis value of the bottom side
            itemName.bottom = circleCenterY + circleBitmap.getHeight() / 4;
            //paint each category item
            canvas.drawBitmap(circleBitmap, itemImg, itemName, paint);
        }
    }

    //position all children within this layout
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //get circle radius
        circleRadius = (getRight() - getLeft()) / 10 * 5;
        //get circle y position
        circleCenterY = (int) (getHeight() * 0.5);
        //get circle x position
        circleCenterX = getWidth() / 2;
        //set position for each item in the list declared in adapter
        for (int j = 0; j < adapterHome.getCount(); j++) {
            //get item
            View itemView = getChildAt(j);
            //get item position
            double itemViewAngle = j * itemIntervalAngle + angle;
            //get length of item's x position from the radius
            int x = circleCenterX + (int) (Math.sin(Math.toRadians(itemViewAngle)) * circleRadius * 0.8);
            //get length of item's y position from the radius
            int y = circleCenterY - (int) (Math.cos(Math.toRadians(itemViewAngle)) * circleRadius * 0.8);
            //get item x-axis position of the left side
            int itemViewLeft = x - itemView.getMeasuredWidth() / 2;
            //get item y-axis position of the top side
            int itemViewTop = y - itemView.getMeasuredHeight() / 2;
            //get item x-axis position of the right side
            int itemViewRight = x + itemView.getMeasuredWidth() / 2;
            //get item y-axis position of the bottom side
            int itemViewBottom = y + itemView.getMeasuredHeight() / 2;
            //set the layout for each item with the above
            itemView.layout(itemViewLeft, itemViewTop, itemViewRight, itemViewBottom);
        }
    }

    public void setAdapter(CricleListViewAdapter adapter) {
        this.adapterHome = adapter;
        //draw items with inflated layout
        refreshList();
    }

    private void refreshList() {
        //remove all item views from the view group
        removeAllViews();
        //set view for each item
        for (int i = 0; i < adapterHome.getCount(); i++) {
            if (i == 0 && angle < -itemIntervalAngle * (adapterHome.getCount() - 1)) {
                angle = -itemIntervalAngle * (adapterHome.getCount() - 1);
            }
            addView(adapterHome.getView(i));
            if (adapterHome.getCount() == 1) {
                setPosition(0);
            }
        }
        //redraw on screen
        invalidate();
    }

    public void setPosition(int position) {
        //get the item angle in the circle
        angle = -position * itemIntervalAngle;
    }
}
