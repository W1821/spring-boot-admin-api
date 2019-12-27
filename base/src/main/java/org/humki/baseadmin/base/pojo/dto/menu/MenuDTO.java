package org.humki.baseadmin.base.pojo.dto.menu;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.humki.baseadmin.common.pojo.dto.BaseDTO;
import org.humki.baseadmin.base.pojo.dto.menu.ButtonDTO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 菜单信息
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@ApiModel("菜单信息")
public class MenuDTO extends BaseDTO {

    @ApiModelProperty(value = "主键id, 增加的时候不要传，修改的时候需要传")
    private Long id;

    @ApiModelProperty(value = "父id", example = "2")
    private Long pid;

    @ApiModelProperty(value = "父id集合", example = "1,2")
    private String pids;

    @ApiModelProperty(value = "菜单路由", example = "/menu/list")
    private String routePath;

    @ApiModelProperty(value = "菜单名称", example = "菜单管理")
    @NotEmpty(message = "menuName cannot be empty")
    private String menuName;

    @ApiModelProperty(value = "菜单名称", example = "菜单管理")
    private String showName;

    @ApiModelProperty(value = "菜单图标" )
    private String icon;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer rank;

    @ApiModelProperty(value = "注册资源 ,分隔", example = "/menu/list")
    private String actions;

    @ApiModelProperty(value = "注册按钮集合")
    private List<ButtonDTO> buttons;

}
