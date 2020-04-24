package com.frankui.gesture;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.frankui.extend.ExtendHelper;
import com.frankui.extend.ExtendScrollView;
import com.frankui.frankui.R;
import com.frankui.utils.TypeUtils;

public class StatusSwitchScrollViewHelper {

    public static void addStatusSwitch(final ExtendScrollView view, final Value value) {
        if (view == null || value == null) {
            return;
        }
        String key = "statusSwitch";
        view.getExtendHelper().setProperty(key, value);
        view.getExtendHelper().getTouchEventCallbacks().put(key, new ExtendHelper.TouchEventCallback<ExtendScrollView>() {
            @Override
            public boolean run(@NonNull ExtendScrollView view, Object value, MotionEvent ev) {
                Value currentValue = TypeUtils.safeCast(value, Value.class);
                if (ev.getAction() == MotionEvent.ACTION_UP) {
                    PageScrollStatus status = calculateNextStatus(view.getScrollY());
                    currentValue.mStatus = status;
                    if (status != PageScrollStatus.NULL) {
                        updateStatus(status, true);
                        return true;
                    }
                }
                return false;
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
        });
        view.getExtendHelper().getInitViewCallbacks().put(key, new ExtendHelper.InitViewCallback<ExtendScrollView>() {
            @Override
            public void run(@NonNull ExtendScrollView view, Object value) {
                Value currentValue = TypeUtils.safeCast(value, Value.class);
                LayoutInflater.from(view.getContext()).inflate(R.layout.custom_scroll_view, view);
                currentValue.mContentLayout = view.findViewById(R.id.ll_content);
                currentValue.mBlankLayout = view.findViewById(R.id.ll_blank);
                view.setVerticalScrollBarEnabled(false); // 隐藏右侧的ScrollBar
                updateBlankHeight();
                updateContentHeight();
                currentValue.mContentLayout = view.findViewById(R.id.ll_content);
                currentValue.mContentLayout.addView(currentValue.mInner);
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
