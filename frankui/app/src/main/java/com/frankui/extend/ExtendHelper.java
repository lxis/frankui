package com.frankui.extend;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExtendHelper {

    @NonNull
    private final Map<String, Object> mExtendFields = new HashMap<>();

    public void add(String key, Object params) {
        mExtendFields.put(key, params);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
