package org.humki.baseadmin.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class IPUtil {

    public static String getClientIP(HttpServletRequest request) {
        String fromSource = "x-real-ip";
        String ip = request.getHeader("x-real-ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
            fromSource = "x-forwarded-for";
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("proxy-client-ip");
            fromSource = "proxy-client-ip";
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("wl-proxy-client-ip");
            fromSource = "wl-proxy-client-ip";
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            fromSource = "request.getRemoteAddr";
        }

        log.debug("get client ip, ip = {}, quoteSource = {}", ip, fromSource);
        return ip;
    }

}
