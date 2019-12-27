package org.humki.baseadmin.common.util;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 路径工具类
 *
 * @author Kael
 */
@Slf4j
public class PathUtil {

    private static final String PROTOCOL_FILE = "file:";
    private static final String ROOT_PATH = "/";
    private static final String JAR = ".jar";
    private static final String UTF8 = "utf-8";


    public static String getRootPath(Class clazz) {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        String filePath;
        try {
            // 转化为utf-8编码
            filePath = URLDecoder.decode(url.getPath(), UTF8);
        } catch (Exception e) {
            log.error("转utf-8编码错误", e);
            return null;
        }
        // idea编辑器中会得到maven目录中依赖jar
        if (filePath.endsWith(JAR)) {
            // 截取路径中的jar包名
            filePath = filePath.substring(0, filePath.lastIndexOf(ROOT_PATH) + 1);
        }
        File file = new File(filePath);
        String absolutePath = file.getAbsolutePath();

        // 使用java -jar 命令运行的项目
        if (filePath.startsWith(PROTOCOL_FILE)) {
            absolutePath = absolutePath.substring(0, absolutePath.indexOf(PROTOCOL_FILE) - 1);
        }
        return absolutePath;
    }

    /**
     * 判断配置的路径是否合法
     */
    public static boolean isInvalidFilePath(String path) {
        if (path == null) {
            return true;
        }
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return false;
        }
        return !file.mkdirs();
    }


}
