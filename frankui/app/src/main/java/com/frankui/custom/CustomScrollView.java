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

    private OnScrollChangeListener mCustomListener;

    private GestureDetector mYGestureDetector;

    private LinearLayout blankLayout;
    public LinearLayout contentLayout;

    // 手势按下时是否在图区范围
    private boolean isDownOnMap = false;

    // blank点击区域的offset修正，这是个trick，没事儿别用
    public int offsetTrick = 0;

    private OnContinueScrollStatus continueScrollListener = null;

    public static final Object TAG = new Object();

    private PageScrollStatus mStatus = PageScrollStatus.BOTTOM;

    private boolean touchable = true;

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
        setTag(TAG);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        bottom = 0;

        LayoutInflater.from(context).inflate(R.layout.custom_scroll_view, this);
        blankLayout = findViewById(R.id.ll_blank);
        contentLayout = findViewById(R.id.ll_content);
    }

    public void setStatusHeight(int top, int bottom) {
        this.top = top;
        this.bottom = bottom;

        setFadingEdgeLength(0);
        setVerticalScrollBarEnabled(false);
        contentLayout.setMinimumHeight(top);
    }

    public LinearLayout getBlankView() {
        return blankLayout;
    }

    public void setBlankHeight(int height) {
        blankLayout.getLayoutParams().height = height;
        blankLayout.setLayoutParams(blankLayout.getLayoutParams());
    }

    public void addContentView(View view) {
        contentLayout.addView(view);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (continueScrollListener != null) {
            if (continueScrollListener.isContinueScroll(ev)) {
                if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_DOWN) {
                    onTouchEvent(ev);
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mMaxVelocity = ViewConfiguration.get(getContext()).getMaximumFlingVelocity();
        boolean canMoveMap;
        if (blankLayout == null) {
            canMoveMap = false;
        } else {
            canMoveMap = canMoveMap(ev, blankLayout);
        }

        if ((canMoveMap && (mStatus == PageScrollStatus.BOTTOM)) || !touchable) {
            return false;
        } else {
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
            try {
                return super.onTouchEvent(ev);
            } catch (Exception e) {
                return false;
            }
        }
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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mYGestureDetector == null) {
            mYGestureDetector = new GestureDetector(getContext(), new YScrollDetector());
        }
        try {
            return super.onInterceptTouchEvent(ev) && mYGestureDetector.onTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
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

    private boolean canMoveMap(MotionEvent ev, View view) {
        boolean inside = isPointInsideView(ev.getY(), view);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            isDownOnMap = inside;
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            isDownOnMap = false;
        }
        return isDownOnMap && inside && ev.getAction() != MotionEvent.ACTION_UP;
    }

    private boolean isPointInsideView(float y, View view) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        return y > rect.top && y < (rect.bottom - getScrollY() + offsetTrick);
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
        PageScrollStatus oldSt = mStatus;
        this.mStatus = status;
        if (mCustomListener != null) {
            mCustomListener.onStatusChanged(oldSt, status);
        }
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

    public void setCustomOnScrollChangeListener(OnScrollChangeListener listener) {
        this.mCustomListener = listener;
    }

    public interface OnScrollChangeListener {
        void onStatusChanged(PageScrollStatus oldSt, PageScrollStatus newSt);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
    }

    public interface OnContinueScrollStatus {
        boolean isContinueScroll(MotionEvent ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // lxis：是为了处理当scroll高于top时自动回弹回top
        if (t > top) {
            setStatus(PageScrollStatus.NULL);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if ((focused instanceof WebView
                || focused instanceof ListView)) {
            return;
        }
        super.requestChildFocus(child, focused);
    }
}