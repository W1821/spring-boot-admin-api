package org.humki.baseadmin.file.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.humki.baseadmin.common.pojo.dto.BaseDTO;

import javax.validation.constraints.NotEmpty;

/**
 * @author saiya
 * @date 2018/9/26 0026
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(callSuper = true)
@ApiModel("base64格式图片上传")
public class ImageBase64UploadDTO extends BaseDTO {

    @ApiModelProperty(value = "base64格式字符串", example = "data:image/png;base64,iVBORw0......")
    @NotEmpty(message = "file cannot be empty")
    private String file;

}
