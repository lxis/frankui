package com.frankui.extend;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class ExtendHelper<T extends View> {

    @NonNull
    private final HashMap<String, Object> mProperty = new HashMap<>();

    public void setProperty(String key, Object value) {
        mProperty.put(key, value);
    }

    /**
     * 回调定义
     */
    public static abstract class MeasureCallback<T extends View> {
        public abstract void run(@NonNull T view, Object value, MeasureParam param);
    }

    public static abstract class LayoutCallback<T extends View> {
        public abstract void run(@NonNull T view, Object value, LayoutParam param);
    }

    public static abstract class DrawCallback<T extends View> {
        public abstract void run(@NonNull T view, Object value, Canvas canvas);
    }

    public static abstract class TouchEventCallback<T extends View> {
        public abstract boolean run(@NonNull T view, Object value, MotionEvent event);
    }

    public static abstract class InitViewCallback<T extends View> {
        public abstract void run(@NonNull T view, Object value);
    }

    /**
     * 回调定义中的参数
     */

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

    /**
     * 回调字典
     */
    @NonNull
    private final HashMap<String, MeasureCallback<T>> mMeasureCallbacks = new HashMap<>();

    @NonNull
    private final HashMap<String, LayoutCallback<T>> mLayoutCallbacks = new HashMap<>();

    @NonNull
    private final HashMap<String, DrawCallback<T>> mDrawCallbacks = new HashMap<>();

    @NonNull
    private final HashMap<String, TouchEventCallback<T>> mTouchEventCallbacks = new HashMap<>();

    @NonNull
    private final HashMap<String, InitViewCallback<T>> mInitViewCallbacks = new HashMap<>();

    private T mView;

    public ExtendHelper(T extendView) {
        mView = extendView;
    }

    /**
     * 调用方法
     */
    public MeasureParam beforeMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMeasureCallbacks.isEmpty()) {
            return null;
        }
        MeasureParam param = ExtendPool.mMeasureParamPool.getOne();
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
            ExtendPool.mMeasureParamPool.returnOne(param);
        }
    }

    public LayoutParam beforeLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mLayoutCallbacks.isEmpty()) {
            return null;
        }
        LayoutParam param = ExtendPool.mLayoutParamPool.getOne();
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
            ExtendPool.mLayoutParamPool.returnOne(param);
        }
    }

    public void initView() {
        for (Map.Entry<String, InitViewCallback<T>> entry : mInitViewCallbacks.entrySet()) {
            if (entry != null) {
                entry.getValue().run(mView, mProperty.get(entry.getKey()));
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        for (Map.Entry<String, TouchEventCallback<T>> entry : mTouchEventCallbacks.entrySet()) {
            if (entry.getValue().run(mView, mProperty.get(entry.getKey()), event)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    public HashMap<String, MeasureCallback<T>> getMeasureCallbacks() {
        return mMeasureCallbacks;
    }

    @NonNull
    public HashMap<String, LayoutCallback<T>> getLayoutCallbacks() {
        return mLayoutCallbacks;
    }

    @NonNull
    public HashMap<String, DrawCallback<T>> getDrawCallbacks() {
        return mDrawCallbacks;
    }

    @NonNull
    public HashMap<String, TouchEventCallback<T>> getTouchEventCallbacks() {
        return mTouchEventCallbacks;
    }

    @NonNull
    public HashMap<String, InitViewCallback<T>> getInitViewCallbacks() {
        return mInitViewCallbacks;
    }
}
