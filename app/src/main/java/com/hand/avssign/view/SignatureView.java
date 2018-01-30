package com.hand.avssign.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hand.avssign.activity.MainActivity;
import com.hand.avssign.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

public class SignatureView extends View {

    private float x;
    private float y;
    private Bitmap bitmap;
    private Boolean modified;
    private Float lastX;
    private Float lastY;

    Paint drawPaint;
    private Path path = new Path();

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.WHITE);
        modified = false;
        drawPaint = new Paint(Paint.DITHER_FLAG);
        drawPaint.setAntiAlias(true);
        drawPaint.setColor(MainActivity.selectedColor);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeWidth(MainActivity.selectedThickness);
        setWillNotDraw(false);
    }

    public void save(String path)
    {
        if(bitmap == null) bitmap =  Bitmap.createBitmap (getWidth(), getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);

        if (!FileUtils.externalStorageAvailable()) {
            Log.d("myLogs", FileUtils.strSDCardUnavailable());
            return;
        }
        File parentDir = new File(MainActivity.imageDir());
        parentDir.mkdirs();
        File signatureFile = new File(parentDir, path);

        try
        {
            FileOutputStream fos = new FileOutputStream(signatureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    public void clear()
    {
        path.reset();
        modified = false;
        invalidate();
    }

    public Boolean getModified() { return modified; }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                lastX = x;
                lastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                if ((Math.abs(x - lastX) >= 1) ||  (Math.abs(y - lastY) >= 1)) modified = true;
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
}