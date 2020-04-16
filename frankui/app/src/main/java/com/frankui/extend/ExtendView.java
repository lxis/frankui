package com.frankui.extend;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Map;

public class ExtendView extends View implements IExtendable<ExtendView> {

    @NonNull
    private final ExtendHelper<ExtendView> mExtendHelper = new ExtendHelper<>(this);

    public ExtendView(Context context) {
        super(context);
    }

    public ExtendView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExtendView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mExtendHelper.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        ExtendHelper.MeasureParam param = mExtendHelper.beforeMeasure(widthMeasureSpec, heightMeasureSpec);
        if (param == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(param.widthMeasureSpec, param.heightMeasureSpec);
            mExtendHelper.afterMeasure(param);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        ExtendHelper.LayoutParam param = mExtendHelper.beforeLayout(changed, left, top, right, bottom);
        if (param == null) {
            super.onLayout(changed, left, top, right, bottom);
        } else {
            super.onLayout(param.changed, param.left, param.top, param.right, param.bottom);
            mExtendHelper.afterLayout(param);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mExtendHelper.beforeDraw(canvas);
        super.onDraw(canvas);
    }

    @Override
    public ExtendHelper<ExtendView> getExtendHelper() {
        return null;
    }
}
