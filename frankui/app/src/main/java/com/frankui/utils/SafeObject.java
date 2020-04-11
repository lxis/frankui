package com.frankui.utils;

import java.util.Comparator;

/**
 * 简化判空
 * Created by lixiang34 on 2018/6/28.
 */
public class SafeObject {
    /**
     * Returns 0 if {@code a == b}, or {@code c.compare(a, b)} otherwise.
     * That is, this makes {@code c} null-safe.
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        if (a == b) {
            return 0;
        }
        return c.compare(a, b);
    }
    /**
     * Null-safe equivalent of {@code a.equals(b)}.
     */
    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }
    /**
     * Returns 0 for null or {@code o.hashCode()}.
     */
    public static int hashCode(Object o) {
        return (o == null) ? 0 : o.hashCode();
    }


    /**
     * Returns {@code o} if non-null, or throws {@code NullPointerException}
     * with the given detail message.
     */
    public static <T> T requireNonNull(T o, String message) {
        if (o == null) {
            throw new NullPointerException(message);
        }
        return o;
    }

    /**
     * Returns "null" for null or {@code o.toString()}.
     */
    public static String toString(Object o) {
        return (o == null) ? "null" : o.toString();
    }

    /**
     * Returns {@code nullString} for null or {@code o.toString()}.
     */
    public static String toString(Object o, String nullString) {
        return (o == null) ? nullString : o.toString();
    }
}
