package com.frankui.gesture;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.frankui.custom.PageScrollStatus;
import com.frankui.extend.ExtendHelper;
import com.frankui.extend.ExtendScrollView;
import com.frankui.frankui.R;

public class StatusSwitchScrollViewHelper {

    public static void addStatusSwitch(final ExtendScrollView view, final Value value) {
        if (view == null || value == null) {
            return;
        }
        String key = "statusSwitch";
        view.getExtendHelper().addValue(key, value);
        view.getExtendHelper().addCallback(key, new ExtendHelper.Callback() {
            @Override
            public boolean onTouchEvent(MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_UP) {
                    PageScrollStatus status = calculateNextStatus(view.getScrollY());
                    value.mStatus = status;
                    if (status != PageScrollStatus.NULL) {
                        updateStatus(status, true);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void initView() {
                LayoutInflater.from(view.getContext()).inflate(R.layout.custom_scroll_view, view);
                value.mContentLayout = view.findViewById(R.id.ll_content);
                value.mBlankLayout = view.findViewById(R.id.ll_blank);
                view.setVerticalScrollBarEnabled(false); // 隐藏右侧的ScrollBar
                updateBlankHeight();
                updateContentHeight();
                value.mContentLayout = view.findViewById(R.id.ll_content);
                value.mContentLayout.addView(value.mInner);
            }

            private void updateContentHeight() {
                if (value.mInner == null) {
                    return;
                }
                ViewGroup.LayoutParams layoutParams = value.mInner.getLayoutParams();
                if (layoutParams == null) {
                    return;
                }
                layoutParams.height = value.mContentHeight;
                value.mInner.requestLayout();
            }

            private void updateBlankHeight() {
                if (value.mBlankLayout == null) {
                    return;
                }
                ViewGroup.LayoutParams layoutParams = value.mBlankLayout.getLayoutParams();
                if (layoutParams == null) {
                    return;
                }
                layoutParams.height = value.mBlankHeight;
                value.mBlankLayout.requestLayout();
            }

            /**
             * 状态切换
             *
             * @param status
             * @param smooth
             */
            private void updateStatus(PageScrollStatus status, boolean smooth) {
                value.mStatus = status;
                switch (status) {
                    case BOTTOM:
                        if (smooth) {
                            view.smoothScrollTo(0, value.mBottom);
                        } else {
                            view.scrollTo(0, value.mBottom);
                        }
                        break;
                    case TOP:
                        if (smooth) {
                            view.smoothScrollTo(0, value.mTop);
                        } else {
                            view.scrollTo(0, value.mTop);
                        }
                        break;
                    default:
                        break;
                }
            }

            private PageScrollStatus calculateNextStatus(int scrollY) {
                if (scrollY > getMid()) {
                    return PageScrollStatus.TOP;
                } else {
                    return PageScrollStatus.BOTTOM;
                }
            }


            private int getMid() {
                return (value.mTop + value.mBottom) / 2;
            }
        });
    }

    public static class Value {
        // 顶部状态
        public int mTop;
        // 底部状态
        public int mBottom;
        // 内容布局，包含顶部空白和下面内容
        public LinearLayout mContentLayout;
        // 内容顶部插入空白控制状态
        public FrameLayout mBlankLayout;
        // 当前状态
        public PageScrollStatus mStatus = PageScrollStatus.BOTTOM;
        // 内部内容
        public View mInner;
        // 顶部插入空白高度
        public int mBlankHeight;
        // 内容高度
        public int mContentHeight;
    }
}
