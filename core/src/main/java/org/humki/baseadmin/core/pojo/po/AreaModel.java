package org.humki.baseadmin.core.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class AreaModel extends BasePO {

    @Id
    @GeneratedValue
    private Long id;

    private String p;

    private String s;








}
