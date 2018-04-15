package com.digitalbithub.magnifize;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class MagnifizeView extends View {
    private static final String LOG_TAG = "Magnifize";
    private Bitmap bitmap;
    private RectF viewBounds;
    private float x, y;
    private float startX, startY;
    private float width, height;
    private float requiredWidth, requiredHeight;
    private int imageSource;


    public MagnifizeView(Context context) {
        super(context);
        init(context, null);
    }

    public MagnifizeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MagnifizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MagnifizeView);
            try {
                imageSource = typedArray.getResourceId(R.styleable.MagnifizeView_imageScr, -1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                typedArray.recycle();
            }
        }
        if (imageSource != -1)
            try {
                bitmap = BitmapFactory.decodeResource(context.getResources(), imageSource);
            } catch (OutOfMemoryError error) {
                bitmap = null;
                Log.e(LOG_TAG, "Image is too large to process. " + error.getMessage());
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        viewBounds = new RectF();
        x = 0;
        y = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();
        float viewWidth = getMeasuredWidth();
        float viewHeight = getMeasuredHeight();
        if (bitmapWidth > bitmapHeight) {
            float scale = viewWidth / bitmapWidth;
            requiredWidth = viewWidth;
            requiredHeight = scale * bitmapHeight;
        } else {
            float scale = viewHeight / bitmapHeight;
            requiredHeight = viewHeight;
            requiredWidth = scale * bitmapWidth;
        }
        width = requiredWidth;
        height = requiredHeight;
        x = (viewWidth - requiredWidth) / 2;
        y = (viewHeight - requiredHeight) / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        System.out.println("onDraw x:" + x + " y:" + y);
        viewBounds.set(x, y, x + requiredWidth, y + requiredHeight);
        canvas.clipRect(viewBounds);
        canvas.drawBitmap(bitmap, null, viewBounds, new Paint());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                zoomImage(true, event);
                break;
            case MotionEvent.ACTION_MOVE:
                move(event);
                break;
            case MotionEvent.ACTION_UP:
                zoomImage(false, event);
                break;
            case MotionEvent.ACTION_CANCEL:
                zoomImage(false, event);
        }
        return true;
    }

    private void move(MotionEvent event) {
        float dx = startX - event.getX();
        float dy = startY - event.getY();
        x += (dx * 2);
        y += (dy * 2);
        if (x > 0) x = 0;
        if (y > 0) y = 0;
        if ((y + requiredHeight) < getMeasuredHeight())
            y = getMeasuredHeight() - requiredHeight;
        if ((x + requiredWidth) < getMeasuredWidth())
            x = getMeasuredWidth() - requiredWidth;
        startX = event.getX();
        startY = event.getY();
        invalidate();
    }

    private void zoomImage(boolean zoom, MotionEvent event) {
        float viewWidth = getMeasuredWidth();
        float viewHeight = getMeasuredHeight();
        if (zoom) {
            startX = event.getX();
            startY = event.getY();
            requiredHeight *= 2;
            requiredWidth *= 2;
            x = startX - (startX * 2);
            y = startY - (startY * 2);

        } else {
            requiredWidth = width;
            requiredHeight = height;
            x = (viewWidth - requiredWidth) / 2;
            y = (viewHeight - requiredHeight) / 2;
        }

        invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }
}