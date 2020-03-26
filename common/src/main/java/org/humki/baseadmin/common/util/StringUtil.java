package org.humki.baseadmin.common.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;


public class StringUtil {

    private static final String NEWLINE = "\n";
    private static final String VERTICAL_LINE = "\\|";
    private static final String COMMA = ",";

    /**
     * 判断是否存在
     */
    public static boolean isEmpty(String data) {
        return data == null || "".equals(data);
    }

    /**
     * 判断是否存在
     */
    public static boolean isNotEmpty(String data) {
        return !isEmpty(data);
    }

    /**
     * 判断是否相等
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        if (str1.length() != str2.length()) {
            return false;
        }
        return str1.equals(str2);
    }

    /**
     * 判断是否相等，忽略大小写
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        if (str1.length() != str2.length()) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }

    /**
     * 剪切掉换行
     */
    public static String[] splitNewline(@Nullable String data) {
        if (StringUtil.isEmpty(data)) {
            return null;
        }
        return data.split(NEWLINE);
    }

    /**
     * 剪切掉换行
     */
    public static String[] splitComma(@Nullable String data) {
        if (StringUtil.isEmpty(data)) {
            return null;
        }
        return data.split(COMMA);
    }

    public static String[] splitVerticalLine(@Nullable String data) {
        if (StringUtil.isEmpty(data)) {
            return null;
        }
        return data.split(VERTICAL_LINE);
    }

    /**
     * 替换URL中的参数变量
     *
     * @param url   url
     * @param key   被替换的key
     * @param value 被替换的value
     * @return 替换后的内容
     */
    public static String replaceUrlParam(String url, String key, String value) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        return url.replace("${" + key + "}", value);
    }

}
