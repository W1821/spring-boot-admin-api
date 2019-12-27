package org.humki.baseadmin.base.pojo.po;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.humki.baseadmin.common.pojo.po.BasePO;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 菜单表
 *
 * @author Kael
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false, of = {"id"})
@Table(name = "menu")
public class MenuModel extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父id
     */
    private Long pid;
    /**
     * 所有的父id
     */
    private String pids;
    /**
     * 菜单路由
     */
    private String routePath;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer rank;

    /**
     * 注册RequestMapping ,分隔
     */
    private String actions;

    /**
     * 删除状态，0：否，1：是
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 按钮集合
     * orphanRemoval删除垃圾数据
     */
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "menu_id")
    private Set<ButtonModel> buttons;

}
