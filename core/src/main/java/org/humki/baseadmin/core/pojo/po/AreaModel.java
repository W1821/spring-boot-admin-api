package org.humki.baseadmin.core.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.humki.baseadmin.common.pojo.po.BasePO;

import javax.persistence.*;

/**
 * @author Kael
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "area")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class AreaModel extends BasePO {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    private String p;

    private String s;


}
