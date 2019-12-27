package org.humki.baseadmin.base.repository;


import org.humki.baseadmin.base.pojo.po.MenuModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 * <b>功能：</b><br>

 * <b>日期：</b>2017/6/19 17:10<br>
 *
 * @author Kael
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuModel, Long>, JpaSpecificationExecutor<MenuModel> {

    /**
     * 查询所有deleted菜单
     *
     * @param key 删除状态
     * @return 菜单集合
     */
    List<MenuModel> findByDeletedOrderByRank(int key);

    /**
     * 根据菜单id集合查询
     *
     * @param menuIds 菜单id集合
     * @return 菜单集合
     */
    List<MenuModel> findByIdIn(List<Long> menuIds);

}
