package org.humki.baseadmin.common.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

/**
 * Http请求工具类
 */
@Slf4j
public class OkHttpClientUtil {

    private static final String UTF8 = "utf-8";

    private static final OkHttpClient OK_HTTP_CLIENT;

    static {
        ConnectionPool pool = new ConnectionPool(5, 180L, TimeUnit.SECONDS);
        OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectionPool(pool)
                .followRedirects(false)
                .retryOnConnectionFailure(true)
                .build();
    }

    /**
     * 发送请求
     */
    public static String get(String url) {
        return get(url, UTF8);
    }

    /**
     * 发送请求
     */
    public static String get(String url, String charset) {
        try {
            Request request = new Request.Builder().get().url(url).build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                byte[] b = responseBody.bytes();
                return new String(b, charset);
            }
        } catch (Exception e) {
            log.error("request error, url={}", url, e);
        }
        return null;
    }


    /**
     * Bearer Token 鉴权请求
     *
     * @param url   接口地址
     * @param token 密钥
     * @return 响应字符串, 如果没有则返回null
     */
    public static String getByToken(String url, String token) {
        return getByToken(url, token, UTF8);
    }


    /**
     * Bearer Token 鉴权请求
     *
     * @param url   接口地址
     * @param token 密钥
     * @return 响应字符串, 如果没有则返回null
     */
    public static String getByToken(String url, String token, String charset) {
        try {
            Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .header("Connection", "close")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            // 执行http请求失败
            if (!response.isSuccessful()) {
                log.error("response error, url={}, secret={}, code={}", url, token, response.code());
                return null;
            }
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                byte[] b = responseBody.bytes();
                return new String(b, charset);
            }
        } catch (Exception e) {
            log.error("request fail, url={}, secret={}", url, token, e);
        }
        return null;
    }


}
