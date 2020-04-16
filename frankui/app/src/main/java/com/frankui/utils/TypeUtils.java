package com.frankui.utils;

/**
 * Created by 享 on 2016/8/23.
 */
public class TypeUtils {

    /**
     * 注意如果出现对象本身是null或者类型不匹配等情况，会返回null。
     * 而Java是自动拆箱的，所以将结果赋值给基础类型有可能在自动拆箱的过程中Crash。
     * 也就是虽然SafeCast本身是Safe的，但用SafeCase的结果做拆箱是不Safe的。
     * 需要拆箱可配合SafeUnboxing方法。
     * @param obj
     * @param classType
     * @param <T>
     * @return
     */
    public static <T> T safeCast(Object obj, Class<T> classType) {
        if (obj == null || classType == null || !classType.isInstance(obj)) {
            return null;
        }
        return (T)obj;
    }

    /**
     * 进行拆箱，如果是null则返回默认值。
     * @param value
     * @return
     */
    public static int safeUnboxing(Integer value, int defaultValue){
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 进行拆箱，如果是null则返回默认值。
     * @param value
     * @return
     */
    public static long safeUnboxing(Long value, long defaultValue){
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 进行拆箱，如果是null则返回默认值。
     * @param value
     * @return
     */
    public static float safeUnboxing(Float value, float defaultValue){
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 进行拆箱，如果是null则返回默认值。
     * @param value
     * @return
     */
    public static double safeUnboxing(Double value, double defaultValue){
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 进行拆箱，如果是null则返回默认值。
     * @param value
     * @return
     */
    public static boolean safeUnboxing(Boolean value, boolean defaultValue){
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
