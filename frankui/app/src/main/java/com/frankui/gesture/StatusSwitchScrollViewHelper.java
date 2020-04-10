package com.frankui.gesture;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.frankui.custom.PageScrollStatus;
import com.frankui.extend.ExtendScrollView;

import java.util.Map;

public class StatusSwitchScrollViewHelper {

    public static void addStatusSwitch(ExtendScrollView scrollView, Value params) {
        scrollView.getExtendHelper().add("statusSwitch", params);
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
    }
}
