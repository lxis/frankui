package com.frankui.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象池，优化性能用
 * Created by lixiang34 on 2018/1/31.
 */

public abstract class ObjectPool<T> {

    private static final int DEFAULT_POOL_MAX_COUNT = 10;

    @NonNull
    private final List<T> mPool = new ArrayList<>();

    private int mPoolMaxCount = DEFAULT_POOL_MAX_COUNT;

    public ObjectPool() {

    }

    public ObjectPool(int poolMaxCount) {
        mPoolMaxCount = poolMaxCount;
    }

    protected abstract T create();

    protected void destroy(T item) {

    }

    protected void activate(T item) {

    }

    protected void deactivate(T item) {

    }

    public T getOne() {
        try {
            T item = ListUtils.getItem(mPool, 0);
            if (item == null) {
                return create();
            } else {
                ListUtils.remove(mPool, 0);
                activate(item);
                return item;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // 线程同步出问题的时候会 Crash
            return null;
        }
    }

    public void returnOne(T object) {
        try {
            boolean isFull = ListUtils.getCount(mPool) >= mPoolMaxCount;
            if (isFull) {
                destroy(object);
            } else {
                deactivate(object);
                mPool.add(object);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // 线程同步出问题的时候会 Crash
        }
    }

    public void dispose() {
        mPool.clear();
    }
}
