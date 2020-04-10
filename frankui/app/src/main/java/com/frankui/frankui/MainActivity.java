package com.frankui.frankui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frankui.custom.CustomScrollView;
import com.frankui.custom.PageScrollStatus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomScrollView customScrollView = findViewById(R.id.customScrollView);
        View inner = LayoutInflater.from(this).inflate(R.layout.scroll_view_inner, null);
        setScrollView(customScrollView, inner);
    }

    public void setScrollView(final CustomScrollView scrollView, View innerView) {
        if (scrollView == null) {
            return;
        }
        scrollView.addContentView(innerView);
        ViewGroup.LayoutParams params = innerView.getLayoutParams();
        if (params != null) {
            params.height = 2000;
        }
        scrollView.setIsTwoStatus(true);
        scrollView.setCustomOnScrollChangeListener(new CustomScrollView.OnScrollChangeListener() {
            @Override
            public void onStatusChanged(PageScrollStatus oldSt, PageScrollStatus newSt) {
                if (newSt == PageScrollStatus.TOP) {
                    scrollView.getBlankView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            scrollView.updateStatus(PageScrollStatus.BOTTOM, true);
                        }
                    });
                } else {
                    scrollView.getBlankView().setOnClickListener(null);
                    scrollView.getBlankView().setClickable(false);
                }
            }

            @Override
            public void onScroll(int scrollY) {

            }
        });
        updateLayout(scrollView);
        innerView.requestLayout();
    }

    private void updateLayout(final CustomScrollView scrollView) {
        if (scrollView == null) {
            return;
        }
        int blankHeight = 1500;
        int bottom = 500;
        int top = blankHeight - 500;
        scrollView.setStatusHeight(top, bottom);
        scrollView.setBlankHeight(blankHeight);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.updateStatus(PageScrollStatus.BOTTOM, false);
            }
        }, 1);

    }
}
