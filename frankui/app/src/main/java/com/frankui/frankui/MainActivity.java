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

        ExtendScrollView extendScrollView = findViewById(R.id.extendScrollView);
        initExtendScrollView(extendScrollView);
    }

    private void initExtendScrollView(ExtendScrollView extendScrollView) {
        StatusSwitchScrollViewHelper.Value value = new StatusSwitchScrollViewHelper.Value();
        value.mStatus = PageScrollStatus.BOTTOM;
        value.mBottom = 500;
        value.mTop = 1000;
        value.mBlankHeight = 1500;
        value.mContentHeight = 3000;
        StatusSwitchScrollViewHelper.addStatusSwitch(extendScrollView, value);

        extendScrollView.initView();

        value.mContentLayout = extendScrollView.findViewById(R.id.ll_content);
        View inner = LayoutInflater.from(this).inflate(R.layout.scroll_view_inner, null);
        value.mInner = inner;
        ViewGroup.LayoutParams params = inner.getLayoutParams();
        if (params != null) {
            params.height = value.mContentHeight;
        }
        value.mContentLayout.addView(inner);
    }
}
