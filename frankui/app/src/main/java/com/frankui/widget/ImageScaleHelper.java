package com.frankui.widget;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.frankui.extend.ExtendHelper;
import com.frankui.extend.ExtendImageView;
import com.frankui.utils.TypeUtils;

public class ImageScaleHelper {


    public enum ScaleType {
        RATIO_SOLID_BY_WIDTH // 宽确定后，高根据宽核宽高比确定，Value：高/宽的比例
    }


    public static void setScaleTypeRatio(ExtendImageView view, Value valueEntity) {
        if (view == null || valueEntity == null) {
            return;
        }
        String key = "scaleType";
        view.getExtendHelper().setProperty(key, valueEntity);
        view.getExtendHelper().getMeasureCallbacks().put(key, new ExtendHelper.MeasureCallback<ExtendImageView>() {
            @Override
            public void run(@NonNull ExtendImageView view, Object value, ExtendHelper.MeasureParam param) {
                if (param == null) {
                    return;
                }
                Value currentValue = TypeUtils.safeCast(value, Value.class);
                if (currentValue != null) {
                    if (currentValue.type == ScaleType.RATIO_SOLID_BY_WIDTH) {
                        int width = View.MeasureSpec.getSize(param.widthMeasureSpec);
                        double height = width * currentValue.value;
                        param.heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) height, View.MeasureSpec.EXACTLY);
                        if (view.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            view.setScaleType(ImageView.ScaleType.FIT_XY);
                        } else {
                            view.requestLayout();
                            view.invalidate();
                        }
                    }
                }
            }
        });
    }

    public static class Value {
        public ScaleType type;
        public float value;
    }

}
