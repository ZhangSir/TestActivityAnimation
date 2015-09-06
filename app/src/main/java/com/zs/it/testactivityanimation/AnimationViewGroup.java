package com.zs.it.testactivityanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangshuo on 2015/8/27.
 */
public class AnimationViewGroup extends ViewGroup{

    private float mProgress;

    private int height;

    private float startX, startY;

    public AnimationViewGroup(Context context) {
        super(context);
    }

    public AnimationViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    protected boolean addViewInLayout(View child, int index, LayoutParams params, boolean preventRequestLayout) {
        throwCustomException(getChildCount());
        boolean returnValue = super.addViewInLayout(child, index, params, preventRequestLayout);
        return returnValue;
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        throwCustomException(getChildCount());
        super.addView(child, index, params);
    }

    private void throwCustomException (int numOfChildViews) {
        if (numOfChildViews == 1) {
            throw new IllegalArgumentException("only one child please");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        height = getMeasuredHeight();
        update();
    }


    private void update() {
        if(isInEditMode()) {
            return;
        }

        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if(isInEditMode() || mProgress >= 1f) {
            super.dispatchDraw(canvas);
            return;
        }

        canvas.save();

        float r = height * mProgress;
        Path path = new Path();
        path.addCircle(startX, startY, r, Path.Direction.CCW);
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
        canvas.restore();


    }


}
