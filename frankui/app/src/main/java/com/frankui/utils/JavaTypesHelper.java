/**
 *
 */
package com.frankui.utils;

/**
 * <h1>安全的类型转换</h1> <br>
 * 把String类型的转换为其他类型 <br>
 * 内部有try-cathc捕获异常并使用BdLog.e打印错误日志。 内部方法都为static方法，可直接使用，如：
 * <p/>
 * <pre>
 * long userId =
 *   JavaTypesHelper.toLong(account, 0);
 * </pre>
 *
 * @author liukaixuan
 */
public abstract class JavaTypesHelper {

    /**
     * 把String类型转换为int型，异常则返回默认值
     *
     * @param val
     * @param defaultValue
     *
     * @return
     */
    public static int toInt(String val, int defaultValue) {
        if (val == null || val.isEmpty()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            // Do nothing
        }

        return defaultValue;
    }

    /**
     * 把String类型转换为long型，异常则返回默认值
     *
     * @param val
     * @param defaultValue
     *
     * @return
     */
    public static long toLong(String val, long defaultValue) {
        if (val == null || val.isEmpty()) {
            return defaultValue;
        }

        try {
            return Long.parseLong(val);
        } catch (Exception e) {
            // Do nothing
        }

        return defaultValue;
    }

    /**
     * 把String类型转换为float型，异常则返回默认值
     *
     * @param val
     * @param defaultValue
     *
     * @return
     */
    public static float toFloat(String val, float defaultValue) {
        if (val == null || val.isEmpty()) {
            return defaultValue;
        }

        try {
            return Float.parseFloat(val);
        } catch (Exception e) {
            // Do nothing
        }

        return defaultValue;
    }

    /**
     * 把String类型转换为double型，异常则返回默认值
     *
     * @param val
     * @param defaultValue
     *
     * @return
     */
    public static double toDouble(String val, double defaultValue) {
        if (val == null || val.isEmpty()) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            // Do nothing
        }

        return defaultValue;
    }

    /**
     * 把String类型转换为boolean型，异常则返回默认值
     *
     * @param val
     * @param defaultValue
     *
     * @return
     */
    public static boolean toBoolean(String val, boolean defaultValue) {
        if (val == null) {
            return defaultValue;
        }

        try {
            return Boolean.parseBoolean(val);
        } catch (Exception e) {
            // Do nothing
        }

        return defaultValue;
    }

}
