package org.humki.baseadmin.base.pojo.dto.role;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.humki.baseadmin.common.pojo.dto.BaseDTO;

/**
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@ApiModel("角色基本信息")
public class  BaseRoleDTO extends BaseDTO {

    @ApiModelProperty(value = "id主键")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

}
