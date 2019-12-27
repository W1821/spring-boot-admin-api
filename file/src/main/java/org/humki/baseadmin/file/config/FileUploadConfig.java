package org.humki.baseadmin.file.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.humki.baseadmin.common.util.PathUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * <br>
 * <b>功能：</b>文件上传配置<br>
 *
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Kael
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadConfig {

    private static final String KB = "KB";
    private static final String MB = "MB";

    /**
     * 文件缓存位置
     */
    private String location;
    /**
     * 统一文件大小限制 KB,MB
     */
    private String maxFileSize = "3MB";
    /**
     * 设置图片大小限制 KB,MB
     */
    private String maxImageSize = "2MB";
    /**
     * 设置总上传数据总大小 KB,MB
     */
    private String maxRequestSize = "3MB";

    /**
     * 所有上传文件根目录
     */
    private String rootPath;
    /**
     * 图片访问映射前缀
     */
    private String imageUrlPrefix = "/public/image";
    /**
     * 图片文件映射匹配
     */
    private String resourceHandler = "/public/image/**";

    public long getMaxSize(String size) {
        if (StringUtils.isEmpty(size)) {
            return -1;
        }
        size = size.toUpperCase().substring(0, size.length() - 2);
        if (size.endsWith(KB)) {
            return Long.valueOf(size) * 1024L;
        } else {
            return size.endsWith(MB) ? Long.valueOf(size) * 1024L * 1024L : Long.valueOf(size);
        }
    }

    public String getLocation() {
        // 如果没有配置文件上传根目录，默认使用当前项目目录
        if (PathUtil.isInvalidFilePath(location)) {
            this.location = PathUtil.getRootPath(this.getClass()) + File.separator + "upload" + File.separator + "temp";
        }
        return this.location;
    }

    public String getRootPath() {
        // 如果没有配置文件上传根目录，默认使用当前项目目录
        if (PathUtil.isInvalidFilePath(rootPath)) {
            this.rootPath = PathUtil.getRootPath(this.getClass()) + File.separator + "upload" + File.separator + "file";
        }
        return this.rootPath;
    }
}
