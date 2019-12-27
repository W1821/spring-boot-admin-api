package org.humki.baseadmin.base.pojo.dto.role;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.humki.baseadmin.base.pojo.dto.menu.MenuDTO;
import org.humki.baseadmin.common.pojo.dto.BaseDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@ApiModel("角色信息")
public class RoleDTO extends BaseDTO {

    @ApiModelProperty(value = "id主键")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    @NotEmpty(message = "roleName cannot be empty")
    private String roleName;

    @ApiModelProperty(value = "角色状态, 0:启用 ，1:禁用")
    @Pattern(regexp = "^0|1$", message = "accountStatus is illegal parameter")
    private String roleStatus;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "角色关联菜单的id")
    private List<Long> menuIds;

    @ApiModelProperty(value = "角色关联菜单的id")
    private List<Long> buttonIds;

}
