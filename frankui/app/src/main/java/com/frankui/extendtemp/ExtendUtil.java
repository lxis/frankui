package com.frankui.extendtemp;

import android.view.View;

public class ExtendUtil {
    public static <T extends View> void addMeasureCallback(IExtendable<T> view, String key, ExtendHelper.MeasureCallback<T> callback) {
        if (view == null) {
            return;
        }
        ExtendHelper<T> helper = view.getExtendHelper();
        if (helper != null) {
            helper.addMeasureCallback(key, callback);
        }
    }

    public static <T extends View> void removeMeasureCallback(IExtendable<T> view, String key) {
        if (view == null) {
            return;
        }
        ExtendHelper<T> helper = view.getExtendHelper();
        if (helper != null) {
            helper.removeMeasureCallback(key);
        }
    }

    public static <T extends View> void addLayoutCallback(IExtendable<T> view, String key, ExtendHelper.LayoutCallback<T> callback) {
        if (view == null) {
            return;
        }
        ExtendHelper<T> helper = view.getExtendHelper();
        if (helper != null) {
            helper.addLayoutCallback(key, callback);
        }
    }

    public static <T extends View> void removeLayoutCallback(IExtendable<T> view, String key) {
        if (view == null) {
            return;
        }
        ExtendHelper<T> helper = view.getExtendHelper();
        if (helper != null) {
            helper.removeLayoutCallback(key);
        }
    }

    public static <T extends View> void addDrawCallback(IExtendable<T> view, String key, ExtendHelper.DrawCallback<T> callback) {
        if (view == null) {
            return;
        }
        ExtendHelper<T> helper = view.getExtendHelper();
        if (helper != null) {
            helper.addDrawCallback(key, callback);
        }
    }

    public static <T extends View> void removeDrawCallback(IExtendable<T> view, String key) {
        if (view == null) {
            return;
        }
        ExtendHelper<T> helper = view.getExtendHelper();
        if (helper != null) {
            helper.removeDrawCallback(key);
        }
    }

    public static <T extends View> void setProperty(IExtendable<T> view, String key, Object value) {
        if (view == null) {
            return;
        }
        ExtendHelper<T> helper = view.getExtendHelper();
        if (helper != null) {
            helper.setProperty(key, value);
        }
    }
}
