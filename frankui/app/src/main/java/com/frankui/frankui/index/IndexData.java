package com.frankui.frankui.index;

public class IndexData {
    private String mText;
    private Class<?> mClz;

    public IndexData(String text) {
        mText = text;
    }

    public IndexData(String text, Class<?> clz) {
        mText = text;
        mClz = clz;
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
}
