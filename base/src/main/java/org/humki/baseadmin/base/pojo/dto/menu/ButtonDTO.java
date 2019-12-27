package org.humki.baseadmin.base.pojo.dto.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.humki.baseadmin.common.pojo.dto.BaseDTO;

import javax.validation.constraints.NotEmpty;

/**
 * 按钮表
 *
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@ApiModel("按钮信息")
public class ButtonDTO extends BaseDTO {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "按钮名称")
    @NotEmpty(message = "buttonName cannot be empty")
    private String buttonName;

    @ApiModelProperty(value = "按钮标识")
    @NotEmpty(message = "code cannot be empty")
    private String code;

    @ApiModelProperty(value = "注册资源 ,分隔")
    private String actions;

    @ApiModelProperty(value = "注册路由 ,分隔")
    private String routePath;

}
