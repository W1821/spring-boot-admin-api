package org.humki.baseadmin.base.pojo.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.humki.baseadmin.base.pojo.dto.role.BaseRoleDTO;
import org.humki.baseadmin.common.pojo.dto.BaseDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ApiModel("用户信息")
public class UserDTO extends BaseDTO {

    @ApiModelProperty(value = "主键id, 增加的时候不要传，修改的时候需要传")
    private Long id;

    @ApiModelProperty(value = "手机号码", example = "18888888888")
    @NotEmpty(message = "phoneNumber cannot be empty")
    private String phoneNumber;

    @ApiModelProperty(value = "密码", example = "xxx")
    private String password;

    @ApiModelProperty(value = "用户名", example = "超级管理员")
    @NotEmpty(message = "userName cannot be empty")
    private String userName;

    @ApiModelProperty(value = "头像相对路径", example = "/public/upload/2018/05/18/xxx.jpg")
    private String headPictureUrl;

    @ApiModelProperty(value = "头像base64字符串", example = "base: ")
    private String headPictureBase64;

    @ApiModelProperty(value = "帐号状态，0：可用，1：不可用", example = "0")
    @Pattern(regexp = "^0|1$", message = "accountStatus is illegal parameter")
    private String accountStatus;

    @ApiModelProperty(value = "创建时间", example = "2018-05-06 12:48:30")
    private Date createTime;

    @ApiModelProperty(value = "角色", example = "[]")
    private List<BaseRoleDTO> roles;


}
