package com.frankui.extendtemp;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;


public class ExtendView extends View implements IExtendable<ExtendView> {

    @NonNull
    private final ExtendHelper<ExtendView> mExtendHelper = new ExtendHelper<>(this);

    public ExtendView(Context context) {
        super(context);
    }

    public ExtendView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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

    @NonNull
    @Override
    public ExtendHelper<ExtendView> getExtendHelper() {
        return mExtendHelper;
    }


}
