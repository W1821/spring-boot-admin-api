package org.humki.baseadmin.core.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.humki.baseadmin.common.pojo.po.BasePO;

import javax.persistence.*;

/**
 * mysql支持id自增长，建议使用自增长主键
 *
 * @author Kael
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "area")
public class AreaModel extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String areaName;


}
