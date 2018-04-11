package com.digitalbithub.scrollablezoom;

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


public class ZoomableImage extends View {
    private static final String LOG_TAG = "log";
    private Bitmap bitmap;
    private RectF viewBounds;
    private float x, y;
    private float startX, startY;
    private float requiredWidth, requiredHeight;
    private int imageSource;


    public ZoomableImage(Context context) {
        super(context);
        init(context, null);
    }

    public ZoomableImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZoomableImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZoomableImage);
            try {
                imageSource = typedArray.getResourceId(R.styleable.ZoomableImage_imageScr, -1);
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
        x = (viewWidth - requiredWidth) / 2;
        y = (viewHeight - requiredHeight) / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
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
        }
        return true;
    }

    private void move(MotionEvent event) {
        float dx = startX - event.getX();
        float dy = startY - event.getY();
        x += dx / 2;
        y += dy / 2;
        if (x > 0) x = 0;
        if (y > 0) y = 0;
        if ((y + requiredHeight) < getMeasuredHeight())
            y = getMeasuredHeight() - requiredHeight;
        if ((x + requiredWidth) < getMeasuredWidth())
            x = getMeasuredWidth() - requiredWidth;
        invalidate();
    }

    private void zoomImage(boolean zoom, MotionEvent event) {
        float viewWidth = getMeasuredWidth();
        float viewHeight = getMeasuredHeight();
        if (zoom) {
            requiredHeight *= 2;
            requiredWidth *= 2;
            startX = event.getX();
            startY = event.getY();
        } else {
            requiredHeight /= 2;
            requiredWidth /= 2;
        }
        x = (viewWidth - requiredWidth) / 2;
        y = (viewHeight - requiredHeight) / 2;
        invalidate();
    }

    private void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }
}