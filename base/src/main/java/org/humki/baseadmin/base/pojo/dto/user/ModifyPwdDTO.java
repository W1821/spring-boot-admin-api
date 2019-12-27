package org.humki.baseadmin.base.pojo.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel("修改密码")
public class ModifyPwdDTO {

    @ApiModelProperty(value = "原密码")
    @NotEmpty(message = "oldPwd cannot be empty")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    @NotEmpty(message = "newPwd cannot be empty")
    private String newPwd;

    @ApiModelProperty(value = "确认密码")
    @NotEmpty(message = "verifiedPwd cannot be empty")
    private String verifiedPwd;

}
