package com.frankui.layout;

import android.support.annotation.IdRes;
import android.support.annotation.XmlRes;
import android.view.View;

import com.frankui.utils.ViewUtils;

public class FrankLayoutBinding {
    public static void bind(View parent, @IdRes int id, @XmlRes int xml) {
        FrankLayout view = ViewUtils.findById(parent, id, FrankLayout.class);
//        view.

    }
}
