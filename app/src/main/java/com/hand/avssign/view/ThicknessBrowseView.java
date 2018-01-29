package com.hand.avssign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hand.avssign.activity.SettingsActivity;

public class ThicknessBrowseView extends View {

    Paint p;
    private int thickness;

    public ThicknessBrowseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        p.setColor(SettingsActivity.selectedColor);
        p.setStrokeWidth(thickness);
        canvas.drawLine(0, 30, getWidth(), 30, p);
    }

    public int getThickness() { return thickness; }

    public void setThickness(int thickness) {
        this.thickness = thickness;
        invalidate();
        requestLayout();
    }

}
