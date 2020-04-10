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

    public static void addStatusSwitch(final ExtendScrollView scrollView, final Value params) {
        scrollView.getExtendHelper().addValue("statusSwitch", params);
        scrollView.getExtendHelper().addCallback("statusSwitch", new ExtendHelper.Callback() {
            @Override
            public boolean onTouchEvent(MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_UP) {
                    PageScrollStatus status = calculateNextStatus(scrollView.getScrollY());
                    params.mStatus = status;
                    if (status != PageScrollStatus.NULL) {
                        updateStatus(status, true);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void initView() {
                LayoutInflater.from(scrollView.getContext()).inflate(R.layout.custom_scroll_view, scrollView);
                params.mContentLayout = scrollView.findViewById(R.id.ll_content);
                params.mBlankLayout = scrollView.findViewById(R.id.ll_blank);
                scrollView.setVerticalScrollBarEnabled(false); // 隐藏右侧的ScrollBar
                updateBlankHeight();

            }

            private void updateBlankHeight() {
                if (params.mBlankLayout == null) {
                    return;
                }
                ViewGroup.LayoutParams layoutParams = params.mBlankLayout.getLayoutParams();
                if (layoutParams == null) {
                    return;
                }
                layoutParams.height = params.mBlankHeight;
                params.mBlankLayout.requestLayout();
            }

            /**
             * 状态切换
             *
             * @param status
             * @param smooth
             */
            private void updateStatus(PageScrollStatus status, boolean smooth) {
                params.mStatus = status;
                switch (status) {
                    case BOTTOM:
                        if (smooth) {
                            scrollView.smoothScrollTo(0, params.mBottom);
                        } else {
                            scrollView.scrollTo(0, params.mBottom);
                        }
                        break;
                    case TOP:
                        if (smooth) {
                            scrollView.smoothScrollTo(0, params.mTop);
                        } else {
                            scrollView.scrollTo(0, params.mTop);
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
                return (params.mTop + params.mBottom) / 2;
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
        public View mInner;
        public int mBlankHeight;
        public int mContentHeight;
    }
}
