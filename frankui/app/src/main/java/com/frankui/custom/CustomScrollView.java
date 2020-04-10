package com.frankui.custom;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;


import com.frankui.frankui.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CustomScrollView extends ScrollView {

    private VelocityTracker mVelocityTracker;
    private int mMaxVelocity;

    private static final int TWO_LEVEL_VEL = 1000;

    private static final int VELOCTITY = 1000;

    // 三种状态scrollto的位置
    public int bottom;
    public int top;

    private LinearLayout blankLayout;
    public LinearLayout contentLayout;

    private PageScrollStatus mStatus = PageScrollStatus.BOTTOM;

    public CustomScrollView(Context context) {
        this(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        bottom = 0;
        LayoutInflater.from(context).inflate(R.layout.custom_scroll_view, this);
        blankLayout = findViewById(R.id.ll_blank);
        contentLayout = findViewById(R.id.ll_content);
        mMaxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
    }

    public void setStatusHeight(int top, int bottom) {
        this.top = top;
        this.bottom = bottom;

        setVerticalScrollBarEnabled(false);
    }

    public void setBlankHeight(int height) {
        blankLayout.getLayoutParams().height = height;
        blankLayout.setLayoutParams(blankLayout.getLayoutParams());
    }

    public void addContentView(View view) {
        contentLayout.addView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        acquireVelocityTracker(ev);
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            final VelocityTracker verTracker = mVelocityTracker;
            if (verTracker != null) {
                verTracker.computeCurrentVelocity(VELOCTITY, mMaxVelocity);
                int initialVelocity = (int) verTracker.getYVelocity();
                PageScrollStatus status = calculateNextStatusWhenTwo(initialVelocity, getScrollY());
                if (status != PageScrollStatus.NULL) {
                    updateStatus(status, true);
                    return true;
                }
                mStatus = status;
            }
        }
        return super.onTouchEvent(ev);
    }

    private PageScrollStatus calculateNextStatusWhenTwo(int initialVelocity, int scrollY) {
        switch (mStatus) {
            case BOTTOM:
                if (scrollY > top) {
                    return PageScrollStatus.NULL;
                } else if ((initialVelocity < 0 && Math.abs(initialVelocity) > TWO_LEVEL_VEL) || scrollY > getMid()) {
                    return PageScrollStatus.TOP;
                } else {
                    return PageScrollStatus.BOTTOM;
                }
            case TOP:
                if (scrollY < getMid()) {
                    return PageScrollStatus.BOTTOM;
                } else if (scrollY < getMid()) {
                    return PageScrollStatus.BOTTOM;
                } else if (scrollY > top) {
                    return PageScrollStatus.NULL;
                } else {
                    return PageScrollStatus.TOP;
                }
            case NULL:
                if (initialVelocity > TWO_LEVEL_VEL) {
                    return PageScrollStatus.TOP;
                } else if (scrollY > top) {
                    return PageScrollStatus.TOP;
                } else if (scrollY > getMid()) {
                    return PageScrollStatus.TOP;
                } else if (scrollY > getMid()) {
                    return PageScrollStatus.TOP;
                } else {
                    return PageScrollStatus.BOTTOM;
                }
            default:
                return PageScrollStatus.BOTTOM;
        }
    }

    private int getMid() {
        return (top + bottom) / 2;
    }

    private void acquireVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 设置状态，不会回调
     *
     * @param status
     */
    public void setStatus(PageScrollStatus status) {
        this.mStatus = status;
    }

    /**
     * 状态切换
     *
     * @param status
     * @param smooth
     */
    public void updateStatus(PageScrollStatus status, boolean smooth) {
        this.mStatus = status;
        switch (status) {
            case BOTTOM:
                if (smooth) {
                    smoothScrollTo(0, bottom);
                } else {
                    scrollTo(0, bottom);
                }
                break;
            case TOP:
                if (smooth) {
                    smoothScrollTo(0, top);
                } else {
                    scrollTo(0, top);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // lxis：是为了处理当scroll高于top时自动回弹回top
        if (t > top) {
            setStatus(PageScrollStatus.NULL);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}