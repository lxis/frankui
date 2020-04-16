package com.frankui.extend;

import com.frankui.utils.ObjectPool;

public class ExtendPool {

    static final ObjectPool<ExtendHelper.MeasureParam> mMeasureParamPool = new ObjectPool<ExtendHelper.MeasureParam>() {
        @Override
        protected ExtendHelper.MeasureParam create() {
            return new ExtendHelper.MeasureParam();
        }
    };

    static final ObjectPool<ExtendHelper.LayoutParam> mLayoutParamPool = new ObjectPool<ExtendHelper.LayoutParam>() {
        @Override
        protected ExtendHelper.LayoutParam create() {
            return new ExtendHelper.LayoutParam();
        }
    };
}
