package org.humki.baseadmin.core.repository;


import org.humki.baseadmin.core.pojo.po.AreaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <br>
 * <b>功能：</b><br>
 *
 * <b>日期：</b>2017/6/19 17:10<br>
 *
 * @author Kael
 */
@Repository
public interface AreaRepository extends JpaRepository<AreaModel, Long>, JpaSpecificationExecutor<AreaModel> {


}
