package org.humki.baseadmin.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.List;

/**
 * json字符串工具类
 * <p>
 * 符合中国国情的格式化工具
 */
public class FastJsonUtil {

    /**
     * 对象转json字符串
     *
     * @param obj 对象
     * @return json字符串
     */
    public static String objToJsonString(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    /**
     * json字符串转对象
     */
    public static <T> T jsonStringToObject(String json, Class<T> classOfT) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        try {
            return JSONObject.parseObject(json, classOfT);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * json字符串转对象
     */
    public static <T> T jsonObjectToJavaObject(JSONObject jsonObject, Class<T> classOfT) {
        if (jsonObject == null) {
            return null;
        }
        try {
            return jsonObject.toJavaObject(classOfT);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * json字符串转List
     */
    public static <T> List<T> jsonStringToList(String json, Class<T> classOfT) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        try {
            return JSONObject.parseArray(json, classOfT);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * 对象转json字符串
     */
    public static JSONObject stringToJsonObject(String json) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtil.isEmpty(json)) {
            return jsonObject;
        }
        try {
            jsonObject = JSONObject.parseObject(json, Feature.OrderedField);
        } catch (Exception ignored) {

        }
        return jsonObject;
    }

    /**
     * 返回String类型
     */
    public static JSONObject getJsonObject(String jsonStr, String key) {
        return stringToJsonObject(jsonStr).getJSONObject(key);
    }

    /**
     * 返回JsonObject类型
     */
    public static String getJsonString(String jsonStr, String key) {
        return stringToJsonObject(jsonStr).getString(key);
    }

    /**
     * 返回int类型
     */
    public static int getJsonInt(String jsonStr, String key) {
        Integer result = stringToJsonObject(jsonStr).getInteger(key);
        if (result == null) {
            return 0;
        }
        return result;
    }

    /**
     * 移除json中某个键值对
     */
    public static Object remove(String jsonStr, String key) {
        JSONObject jsonObject = stringToJsonObject(jsonStr);
        return jsonObject.remove(key);
    }


    /**
     * json 美化
     */
    private static String prettyJson(String json) {
        if (StringUtil.isEmpty(json)) {
            return json;
        }
        JSONObject jsonObject = stringToJsonObject(json);
        return JSONObject.toJSONString(jsonObject, true);
    }

}
