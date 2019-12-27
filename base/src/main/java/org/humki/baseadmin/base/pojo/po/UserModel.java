package org.humki.baseadmin.base.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.humki.baseadmin.common.pojo.po.BasePO;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author Kael
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "user")
public class UserModel extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String userName;
    private String password;

    /**
     * 头像
     */
    private String headPictureUrl;

    /**
     * 帐号状态，0：启用，1：禁用
     */
    private Integer accountStatus;

    /**
     * 删除状态，0：否，1：是
     */
    private Integer deleted;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 超级管理员，0：否，1：是
     */
    private Integer superAdmin;

    /**
     * 用户多对多关联角色
     */
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    private Set<RoleModel> roles;

}
