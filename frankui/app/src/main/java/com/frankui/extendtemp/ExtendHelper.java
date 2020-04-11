package com.frankui.extendtemp;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.frankui.utils.ObjectPool;

import java.util.HashMap;
import java.util.Map;

public class ExtendHelper<T extends View> {

    private static final ObjectPool<MeasureParam> mMeasureParamPool = new ObjectPool<MeasureParam>() {
        @Override
        protected MeasureParam create() {
            return new MeasureParam();
        }
    };

    private static final ObjectPool<LayoutParam> mLayoutParamPool = new ObjectPool<LayoutParam>() {
        @Override
        protected LayoutParam create() {
            return new LayoutParam();
        }
    };

    @NonNull
    private final HashMap<String, Object> mProperty = new HashMap<>();

    @NonNull
    private final HashMap<String, MeasureCallback<T>> mMeasureCallbacks = new HashMap<>();

    @NonNull
    private final HashMap<String, LayoutCallback<T>> mLayoutCallbacks = new HashMap<>();

    @NonNull
    private final HashMap<String, DrawCallback<T>> mDrawCallbacks = new HashMap<>();

    private T mView;

    public ExtendHelper(T extendView) {
        mView = extendView;
    }

    public MeasureParam beforeMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMeasureCallbacks.isEmpty()) {
            return null;
        }
        MeasureParam param = mMeasureParamPool.getOne();
        param.widthMeasureSpec = widthMeasureSpec;
        param.heightMeasureSpec = heightMeasureSpec;
        for (Map.Entry<String, MeasureCallback<T>> entry : mMeasureCallbacks.entrySet()) {
            if (entry != null) {
                entry.getValue().run(mView, mProperty.get(entry.getKey()), param);
            }
        }
        return param;
    }

    public void beforeDraw(Canvas canvas) {
        for (Map.Entry<String, DrawCallback<T>> entry : mDrawCallbacks.entrySet()) {
            if (entry != null) {
                entry.getValue().run(mView, mProperty.get(entry.getKey()), canvas);
            }
        }
    }

    public void afterMeasure(MeasureParam param) {
        if (param != null) {
            mMeasureParamPool.returnOne(param);
        }
    }

    public LayoutParam beforeLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mLayoutCallbacks.isEmpty()) {
            return null;
        }
        LayoutParam param = mLayoutParamPool.getOne();
        param.changed = changed;
        param.left = left;
        param.top = top;
        param.right = right;
        param.bottom = bottom;
        for (Map.Entry<String, LayoutCallback<T>> entry : mLayoutCallbacks.entrySet()) {
            if (entry != null) {
                entry.getValue().run(mView, mProperty.get(entry.getKey()), param);
            }
        }
        return param;
    }

    public void afterLayout(LayoutParam param) {
        if (param != null) {
            mLayoutParamPool.returnOne(param);
        }
    }


    public static abstract class Callback {

    }

    public static abstract class MeasureCallback<T extends View> extends Callback {

        public abstract void run(@NonNull T view, Object value, MeasureParam param);
    }

    public static abstract class LayoutCallback<T extends View> extends Callback {
        public abstract void run(@NonNull T view, Object value, LayoutParam param);
    }

    public static abstract class DrawCallback<T extends View> extends Callback {

        public abstract void run(@NonNull T view, Object value, Canvas canvas);
    }

    public static class MeasureParam {
        public int widthMeasureSpec;
        public int heightMeasureSpec;
    }

    public static class LayoutParam {
        public boolean changed;
        public int left;
        public int top;
        public int right;
        public int bottom;
    }

    public void setProperty(String key, Object value) {
        mProperty.put(key, value);
    }

    public void addMeasureCallback(String key, MeasureCallback<T> callback) {
        if (callback == null || TextUtils.isEmpty(key)) {
            return;
        }
        mMeasureCallbacks.put(key, callback);
    }

    public void removeMeasureCallback(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mMeasureCallbacks.remove(key);
    }

    public void addLayoutCallback(String key, LayoutCallback<T> callback) {
        if (callback == null || TextUtils.isEmpty(key)) {
            return;
        }
        mLayoutCallbacks.put(key, callback);
    }

    public void removeLayoutCallback(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mLayoutCallbacks.remove(key);
    }

    public void addDrawCallback(String key, DrawCallback<T> callback) {
        if (callback == null || TextUtils.isEmpty(key)) {
            return;
        }
        mDrawCallbacks.put(key, callback);
    }

    public void removeDrawCallback(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mDrawCallbacks.remove(key);
    }
}
