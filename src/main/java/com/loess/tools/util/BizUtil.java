package com.loess.tools.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BizUtil {
    /**
     * 判断状态值或枚举值是否属于某个集合
     *
     * @return
     */
    public static boolean in(Integer i, int collection1, int... collection2s) {
        if (i == null)
            return false;
        if (i == collection1)
            return true;
        for (int c : collection2s) {
            if (i == c)
                return true;
        }
        return false;
    }

    public static boolean notIn(Integer i, int... collection2s) {
        if (i == null)
            return false;

        for (int c : collection2s) {
            if (i == c)
                return false;
        }
        return true;
    }

    /**
     * 判断状态值或枚举值是否属于某个集合
     *
     * @return
     */
    public static boolean in(Integer i, int[] collections) {
        if (i == null)
            return false;
        if (collections != null)
            for (int c : collections) {
                if (i == c)
                    return true;
            }
        return false;
    }

    /**
     * 判断字符串是否属于某个集合
     *
     * @return
     */
    public static boolean in(String i, String str2, String... collections) {
        boolean b = in2(i, str2, collections);
//    	System.out.println(i + ":" + b);
        return b;
    }

    public static boolean in2(String i, String str2, String... collections) {
        if (i == null)
            return false;
        if (i.equals(str2)) {
            return true;
        }
        for (String c : collections) {
            if (i.equals(c))
                return true;
        }
        return false;
    }

    public static boolean in(String i, String[] collections) {
        if (i == null)
            return false;
        if (collections != null)
            for (String c : collections) {
                if (i.equals(c))
                    return true;
            }
        return false;
    }

    /**
     * 过滤掉null值为0
     *
     * @param n
     * @return
     */
    public static Long null2Zero(Long n) {
        return n != null ? n : 0;
    }

    /**
     * 过滤掉null值为0
     *
     * @param n
     * @return
     */
    public static Integer null2Zero(Integer n) {
        return n != null ? n : 0;
    }

    /**
     * 合并对象成字符串
     *
     * @param <E>
     * @param list
     * @return
     */
    public static <E> String join(Collection<E> list) {
        return StringUtils.join(list, ",");
    }

    /**
     * 合并对象成字符串
     *
     * @param <E>
     * @param list
     * @return
     */
    public static <E> String join(E[] list) {
        return StringUtils.join(list, ",");
    }

    /**
     * 将字符串数组，转为integer的List
     *
     * @param strs
     * @return
     */
    public static List<Integer> toIntegerList(String[] strs) {
        List<Integer> list = new ArrayList<Integer>(strs.length);
        for (String str : strs) {
            list.add(Integer.parseInt(str));
        }
        return list;
    }
}
