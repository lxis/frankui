package com.frankui.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 与 {@link android.view.View} 相关的常用方法常量集合类。
 *
 * @author rongwensheng
 * @since 2015-03-26
 * ]
 */
public final class ViewUtils {

    /**
     * 尝试从父 {@link android.view.View} 中删除其子 {@link android.view.View} 。
     *
     * @param view 要删除的子 {@link android.view.View} ；不可为 <code>null</code> 。
     * @return 成功则为 <code>true</code> ；否则 <code>false</code> 。
     */
    public static boolean tryRemoveViewFromParent(View view) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return true;
        }
        return false;
    }

    public static <T extends View> T findById(View view, int id) {
        if (view == null) {
            return null;
        }
        try {
            return (T) view.findViewById(id);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public static <T extends View> T findById(View view, int id, Class<T> classType) {
        if (view == null || id <= 0 || classType == null) {
            return null;
        }
        View itemView = view.findViewById(id);
        if (!classType.isInstance(itemView)) {
            return null;
        }
        return (T) itemView;
    }

    public static void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public static void setVisibility(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public static void setOnClickListener(View view, View.OnClickListener l) {
        if (view != null) {
            view.setOnClickListener(l);
        }
    }

    public static Context c(View view) {
        return view == null ? null : view.getContext();
    }

}
