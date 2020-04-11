package com.frankui.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * 常用List操作
 * <p>
 * <b><font color="red">注意线程安全！</font></b>
 *
 * @author yaohaitong
 */
public class ListUtils {

    public static <T> void clear(List<T> list) {
        if (list == null) {
            return;
        }
        list.clear();
    }

    public static <T> int getCount(List<T> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return list.size();
    }

    public static <T> T getItem(List<T> list, int position) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (position < 0 || position >= list.size()) {
            return null;
        }
        return list.get(position);
    }

    public static <T> T getItem(T[] list, int position) {
        if (list == null || list.length == 0) {
            return null;
        }
        if (position < 0 || position >= list.length) {
            return null;
        }
        return list[position];
    }

    public static <T> int getPosition(List<T> list, T itemData) {
        if (list == null || list.isEmpty() || itemData == null) {
            return -1;
        }
        return list.indexOf(itemData);
    }

    public static List<String> subList(List<String> list, int start, int end) {
        int listSize = getCount(list);
        if (listSize <= 0) {
            return null;
        }
        if (start < 0 || end > listSize) {
            return null;
        }
        return list.subList(start, end);
    }

    public static <T> boolean isEmpty(List<T> list) {
        return (getCount(list) <= 0);
    }

    public static <T> T remove(List<T> list, int position) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (position < 0 || position >= list.size()) {
            return null;
        }
        return list.remove(position);
    }

    public static <T> void remove(List<T> list, T position) {
        if (list == null || list.isEmpty()) {
            return;
        }
        list.remove(position);
    }

    public static <T> boolean add(List<T> list, T item) {
        return list != null && list.add(item);
    }

    /**
     * 在指定index插入item
     *
     * @param list     操作的列表
     * @param position 插入位置
     * @param item     插入项
     * @param <T>      item类型
     * @return 是否插入成功
     */
    public static <T> boolean add(List<T> list, int position, T item) {
        if (list == null || position > list.size() || position < 0) {
            return false;
        }
        list.add(position, item);
        return true;
    }

    /**
     * 进行List泛型的转换，因为Java不支持协变，需要新建List并一项一项的做类型转换
     *
     * @param originalList 原List
     * @param <T>          原List的Item的泛型形参
     * @param <V>          类型转换后List的Item的泛型形参
     * @return 类型转换后List
     */
    public static <T extends V, V> List<V> castList(List<T> originalList) {
        if (originalList == null) {
            return null;
        }
        List<V> newList = new ArrayList<V>();
        for (T item : originalList) {
            newList.add((V) item);
        }
        return newList;
    }

    public static <T> void swap(List<T> list, int first, int second) {
        if (getCount(list) <= first || getCount(list) <= second) {
            return;
        }
        T firstItem = list.get(first);
        T secondItem = list.get(second);
        list.set(first, secondItem);
        list.set(second, firstItem);
    }
}
