package org.humki.baseadmin.core.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.humki.baseadmin.common.pojo.po.BasePO;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * uuid作为主键
 *
 * @author Kael
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "demo")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class DemoModel extends BasePO {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    private String demoName;

    /**
     * 创建时间
     */
    @Column(updatable = false)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
