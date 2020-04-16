package com.frankui.frankui.index;

public class IndexData {
    private String mText;
    private Class<?> mClz;
    private boolean mIsIndex;

    public IndexData(String text) {
        mText = text;
    }

    public IndexData(String text, boolean isIndex) {
        mText = text;
        mIsIndex = isIndex;
    }

    public IndexData(String text, Class<?> clz) {
        mText = text;
        mClz = clz;
        mIsIndex = true;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Class<?> getClz() {
        return mClz;
    }

    public void setClz(Class<?> clz) {
        mClz = clz;
    }

    public boolean isIndex() {
        return mIsIndex;
    }

    public void setIsIndex(boolean isIndex) {
        mIsIndex = isIndex;
    }
}
