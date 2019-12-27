package org.humki.baseadmin.base.pojo.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.humki.baseadmin.common.pojo.dto.base.search.SearchDTO;

import javax.validation.constraints.NotEmpty;


/**
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ApiModel("用户列表查询条件")
public class UserSearchDTO extends SearchDTO {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号码", example = "18888888888")
    private String phoneNumber;

}
