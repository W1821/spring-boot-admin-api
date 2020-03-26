package org.humki.baseadmin.file.controller;


import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.constant.GlobalEnum;
import org.humki.baseadmin.common.controller.BaseController;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.humki.baseadmin.common.util.UploadFileUtil;
import org.humki.baseadmin.file.pojo.dto.ImageBase64UploadDTO;
import org.humki.baseadmin.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * 文件上传
 *
 * @author Kael
 */
@RestController
@RequestMapping("/file")
@SuppressWarnings("unchecked")
public class FileController extends BaseController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 单图片上传
     *
     * @param base64UploadDTO 上传的base64格式
     * @return 图片的url
     */
    @RequestMapping(value = "/upload/base64", method = RequestMethod.POST)
    public ResponseMessage<String> uploadBase64(@RequestBody @Valid ImageBase64UploadDTO base64UploadDTO) {
        String path = fileService.uploadBase64(base64UploadDTO.getFile());
        return ResponseMessageUtil.success(path);
    }

    /**
     * 单图片上传
     *
     * @param file 上传的文件
     * @return 图片的url
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    public ResponseMessage<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1100);
        }
        // 判断文件是否是图片
        if (isNotSupportedImage(file)) {
            ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1101);
        }
        // 判断图片是否是真图片
        if (UploadFileUtil.isRealImage(file)) {
            ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1101);
        }
        String path = fileService.uploadImage(file);
        return ResponseMessageUtil.success(path);
    }

    /**
     * 判断文件是否是图片
     */
    private boolean isNotSupportedImage(MultipartFile file) {
        return !GlobalEnum.IMAGE_FIELDS.containsIgnoreCase(UploadFileUtil.getFileSuffix(file.getOriginalFilename()));
    }

}
