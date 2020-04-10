package com.frankui.extend;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;

public class ExtendHelper {

    @NonNull
    private final Map<String, Object> mExtendFields = new HashMap<>();

    public void addValue(String key, Object params) {
        mExtendFields.put(key, params);
    }

    @NonNull
    private final Map<String, Callback> mCallback = new HashMap<>();

    public interface Callback {
        boolean onTouchEvent(MotionEvent event);

        void initView();
    }

    public final Map<String, Callback> getCallback() {
        return mCallback;
    }

    public void addCallback(String key, Callback callback) {
        mCallback.put(key, callback);
    }


}
