package org.humki.baseadmin.base.pojo.po;

import lombok.*;
import org.humki.baseadmin.common.pojo.po.BasePO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 角色表实体类
 *
 * @author Kael
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
@Table(name = "role")
public class RoleModel extends BasePO {

    /**
     * id主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色状态, 0:启用 ，1:禁用
     */
    private Integer roleStatus;
    /**
     * 角色描述
     */
    private String description;

    /**
     * 创建时间
     */
    @Column(updatable = false)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 删除状态
     */
    private Integer deleted;

    @JoinTable(name = "role_menu", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<MenuModel> menus;

    @JoinTable(name = "role_button", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "button_id")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<ButtonModel> buttons;

}
