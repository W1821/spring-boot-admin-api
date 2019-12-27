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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseMessage<String> uploadImageSingle(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1100);
        }
        // 判断文件是否是图片
        if (!isImage(file)) {
            ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1101);
        }
        String path = fileService.uploadImage(file);
        return ResponseMessageUtil.success(path);
    }

    /**
     * 多图片上传
     *
     * @param request http请求
     * @return 多个图片的url
     */
    @RequestMapping(value = "/upload/multiple/image", method = RequestMethod.POST)
    public ResponseMessage<List<String>> uploadImageMultiple(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        List<String> imageUrlList = new ArrayList<>();
        files.forEach(file -> {
            if (file != null && isImage(file)) {
                String imageUrl = fileService.uploadImage(file);
                if (imageUrl != null) {
                    imageUrlList.add(imageUrl);
                }
            }
        });
        return ResponseMessageUtil.success(imageUrlList);
    }

    /**
     * 判断文件是否是图片
     */
    private boolean isImage(MultipartFile file) {
        return GlobalEnum.IMAGE_FIELDS.containsIgnoreCase(UploadFileUtil.getFileSuffix(file.getOriginalFilename()));
    }

}
