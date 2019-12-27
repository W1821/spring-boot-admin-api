package org.humki.baseadmin.base.pojo.dto.role;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.humki.baseadmin.common.pojo.dto.base.search.SearchDTO;

import javax.validation.constraints.Pattern;


/**
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel("角色列表分页查询条件")
public class RoleSearchDTO extends SearchDTO {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色状态")
    @Pattern(regexp = "^0|1$", message = "roleStatus is illegal parameter")
    private String roleStatus;

}
