package org.humki.baseadmin.file.service;


import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.util.ExceptionUtil;
import org.humki.baseadmin.common.util.UploadFileUtil;
import org.humki.baseadmin.file.config.FileUploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Kael
 */
@Service
public class FileService {

    private final FileUploadConfig fileUploadConfig;

    @Autowired
    public FileService(FileUploadConfig fileUploadConfig) {
        this.fileUploadConfig = fileUploadConfig;
    }

    /**
     * 上传图片
     *
     * @param file 图片文件
     * @return 图片访问url
     */
    public String uploadBase64(String file) {
        String rootPath = fileUploadConfig.getRootPath();
        String imageUrlPrefix = fileUploadConfig.getImageUrlPrefix();
        return UploadFileUtil.writeImageBase64ToFile(file, rootPath, imageUrlPrefix);
    }

    /**
     * 上传图片
     *
     * @param file 图片文件
     * @return 图片访问url
     */
    public String uploadImage(MultipartFile file) {
        // 判断文件大小
        long fileSize = file.getSize();
        long maxImageSize = fileUploadConfig.getMaxSize(fileUploadConfig.getMaxImageSize());
        if (fileSize > maxImageSize) {
            ExceptionUtil.throwError(GlobalCodeEnum.ErrorCode.ERROR_1102);
        }

        String rootPath = fileUploadConfig.getRootPath();
        String imageUrlPrefix = fileUploadConfig.getImageUrlPrefix();
        return UploadFileUtil.writeSingleFileToDisk(file, rootPath, imageUrlPrefix);
    }


}
