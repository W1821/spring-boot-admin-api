package org.humki.baseadmin.base.repository;


import org.humki.baseadmin.base.pojo.po.ButtonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 * <b>功能：</b><br>
 *
 * <b>日期：</b>2017/6/19 17:10<br>
 *
 * @author Kael
 */
@Repository
public interface ButtonRepository extends JpaRepository<ButtonModel, Long>, JpaSpecificationExecutor<ButtonModel> {

    /**
     * 根据按钮id集合查询
     *
     * @param buttonIds 按钮id集合
     * @return 按钮集合
     */
    List<ButtonModel> findByIdIn(List<Long> buttonIds);

}
