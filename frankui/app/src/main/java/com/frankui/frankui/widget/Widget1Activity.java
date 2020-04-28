package com.frankui.frankui.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.frankui.extend.ExtendImageView;
import com.frankui.frankui.R;
import com.frankui.widget.ImageScaleHelper;

public class Widget1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget1_activity);

        ExtendImageView extendScrollView = findViewById(R.id.image);
        initImageView(extendScrollView);
    }

    private void initImageView(ExtendImageView extendScrollView) {
        ImageScaleHelper.Value value = new ImageScaleHelper.Value();
        value.type = ImageScaleHelper.ScaleType.RATIO_SOLID_BY_WIDTH;
        value.value = 1;
        ImageScaleHelper.setScaleTypeRatio(extendScrollView, value);
    }
}
