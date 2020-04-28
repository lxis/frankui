package com.frankui.extend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class ExtendImageView extends ImageView implements IExtendable<ExtendImageView> {
    @NonNull
    private final ExtendHelper<ExtendImageView> mExtendHelper = new ExtendHelper<ExtendImageView>(this);

    public ExtendImageView(Context context) {
        super(context);
    }

    public ExtendImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExtendImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mExtendHelper.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
    @NonNull
    public ExtendHelper<ExtendImageView> getExtendHelper() {
        return mExtendHelper;
    }
}
