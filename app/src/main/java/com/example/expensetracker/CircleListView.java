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

    private static final double intervalAngel = 25.7;
    int circleR;
    int ccx;
    int ccy;
    double angel = 0;
    private Bitmap circleBitmap = null;
    private Rect src;
    private Rect dst;
    private Paint paint;
    private CategoryAdapterHome adapter = new CategoryAdapterHome(this) {
        @Override
        public View getView(int position) {
            return new View(getContext());
        }

    };

    public CircleListView(Context context) {
        super(context);
        setWillNotDraw(false);
        paint = new Paint();
    }

    public CircleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        paint = new Paint();
    }

    public void setAdapter(CategoryAdapterHome adapter) {
        this.adapter = adapter;
        refreshList();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (circleBitmap != null) {
            if (src == null) {
                src = new Rect();
            }
            src.left = 0;
            src.top = 0;
            src.right = circleBitmap.getWidth();
            src.bottom = circleBitmap.getHeight();
            if (dst == null) {
                dst = new Rect();
            }
            dst.left = ccx - circleBitmap.getWidth()/4;
            dst.top = ccy - circleBitmap.getHeight()/4;
            dst.right = ccx + circleBitmap.getWidth()/4;
            dst.bottom = ccy + circleBitmap.getHeight()/4;
            canvas.drawBitmap(circleBitmap, src, dst, paint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        circleR = (getRight() - getLeft()) / 10 * 5;
        ccy = (int) (getHeight() * 0.5);
        ccx = getWidth() / 2;
        for (int i = 0; i < adapter.getCount(); i++) {
            View childView = getChildAt(i);
            double childViewAngel = i * intervalAngel + angel;
            int x = ccx + (int) (Math.sin(Math.toRadians(childViewAngel)) * circleR * 0.8);
            int y = ccy - (int) (Math.cos(Math.toRadians(childViewAngel)) * circleR * 0.8);
            int vl = x - childView.getMeasuredWidth() / 2;
            int vt = y - childView.getMeasuredHeight() / 2;
            int vr = x + childView.getMeasuredWidth() / 2;
            int vb = y + childView.getMeasuredHeight() / 2;
            childView.layout(vl, vt, vr, vb );
        }
    }

    protected void refreshList() {
        removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (i == 0 && angel < -intervalAngel * (adapter.getCount() - 1)) {
                angel = -intervalAngel * (adapter.getCount() - 1);
            }
            addView(adapter.getView(i));
            if (adapter.getCount() == 1) {
                setPosition(0);
            }
        }
        invalidate();
    }

    protected void setPosition(int position) {
        angel = -position * intervalAngel;
    }
}
