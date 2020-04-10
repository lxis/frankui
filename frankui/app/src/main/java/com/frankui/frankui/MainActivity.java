package com.frankui.frankui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frankui.custom.CustomScrollView;
import com.frankui.custom.PageScrollStatus;
import com.frankui.extend.ExtendScrollView;
import com.frankui.gesture.StatusSwitchScrollViewHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomScrollView customScrollView = findViewById(R.id.customScrollView);
        View inner = LayoutInflater.from(this).inflate(R.layout.scroll_view_inner, null);
        setScrollView(customScrollView, inner);

//        ExtendScrollView extendScrollView = findViewById(R.id.extendScrollView);
//        initExtendScrollView(extendScrollView);
    }

    private void initExtendScrollView(ExtendScrollView extendScrollView) {
        StatusSwitchScrollViewHelper.Value value = new StatusSwitchScrollViewHelper.Value();
        value.mStatus = PageScrollStatus.BOTTOM;
        value.mBottom = 500;
        value.mTop = 1000;
        value.mBlankHeight = 1500;

        value.mContentLayout = extendScrollView.findViewById(R.id.ll_content);
        View inner = LayoutInflater.from(this).inflate(R.layout.scroll_view_inner, null);
        value.mInner = inner;
        ViewGroup.LayoutParams params = inner.getLayoutParams();
        if (params != null) {
            params.height = 3000;
        }
        value.mContentLayout.addView(inner);
        StatusSwitchScrollViewHelper.addStatusSwitch(extendScrollView, value);
    }

    public void setScrollView(final CustomScrollView scrollView, View innerView) {
        if (scrollView == null) {
            return;
        }
        scrollView.addContentView(innerView);
        ViewGroup.LayoutParams params = innerView.getLayoutParams();
        if (params != null) {
            params.height = 3000;
        }
        updateLayout(scrollView);
    }

    private void updateLayout(final CustomScrollView scrollView) {
        if (scrollView == null) {
            return;
        }
        int blankHeight = 1500;
        int bottom = 500;
        int top = 1000;
        scrollView.setStatusHeight(top, bottom, blankHeight);
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.updateStatus(PageScrollStatus.BOTTOM, false);
            }
        }, 1);
    }
}
