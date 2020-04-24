package com.frankui.frankui.gesture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.frankui.gesture.PageScrollStatus;
import com.frankui.extend.ExtendScrollView;
import com.frankui.frankui.R;
import com.frankui.gesture.StatusSwitchScrollViewHelper;

public class Gesture1Activity extends AppCompatActivity {

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
        value.mInner = LayoutInflater.from(this).inflate(R.layout.scroll_view_inner, null);
        StatusSwitchScrollViewHelper.addStatusSwitch(extendScrollView, value);
        extendScrollView.initView();
    }
}
