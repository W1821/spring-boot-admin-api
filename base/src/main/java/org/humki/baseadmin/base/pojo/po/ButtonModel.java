package org.humki.baseadmin.base.pojo.po;

import lombok.*;
import org.humki.baseadmin.common.pojo.po.BasePO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 按钮表表
 *
 * @author Kael
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Table(name = "button")
public class ButtonModel extends BasePO {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 按钮名称
     */
    private String name;

    /**
     * 按钮标识
     */
    private String code;

    /**
     * 注册资源 ,分隔
     */
    private String actions;

    /**
     * 注册路由
     */
    private String routePath;


}
