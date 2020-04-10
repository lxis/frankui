package com.frankui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import com.frankui.frankui.R;

// 两状态列表
public class CustomScrollView extends ScrollView {

    // 顶部状态
    private int mTop;
    // 底部状态
    private int mBottom;
    // 内容布局，包含顶部空白和下面内容
    private LinearLayout mContentLayout;
    // 内容顶部插入空白控制状态
    private FrameLayout mBlankLayout;
    // 当前状态
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
        LayoutInflater.from(context).inflate(R.layout.custom_scroll_view, this);
        mContentLayout = findViewById(R.id.ll_content);
        mBlankLayout = findViewById(R.id.ll_blank);
        setVerticalScrollBarEnabled(false); // 隐藏右侧的ScrollBar
    }

    public void setStatusHeight(int top, int bottom) {
        mTop = top;
        mBottom = bottom;
    }

    public void setBlankHeight(int height) {
        if (mBlankLayout == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = mBlankLayout.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        layoutParams.height = height;
        mBlankLayout.requestLayout();
    }

    public void addContentView(View view) {
        mContentLayout.addView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            PageScrollStatus status = calculateNextStatus(getScrollY());
            mStatus = status;
            if (status != PageScrollStatus.NULL) {
                updateStatus(status, true);
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    private PageScrollStatus calculateNextStatus(int scrollY) {
        if (scrollY > getMid()) {
            return PageScrollStatus.TOP;
        } else {
            return PageScrollStatus.BOTTOM;
        }
    }

    private int getMid() {
        return (mTop + mBottom) / 2;
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
                    smoothScrollTo(0, mBottom);
                } else {
                    scrollTo(0, mBottom);
                }
                break;
            case TOP:
                if (smooth) {
                    smoothScrollTo(0, mTop);
                } else {
                    scrollTo(0, mTop);
                }
                break;
            default:
                break;
        }
    }
}