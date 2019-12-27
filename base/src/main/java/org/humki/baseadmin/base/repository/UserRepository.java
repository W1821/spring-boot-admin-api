package org.humki.baseadmin.base.repository;


import org.humki.baseadmin.base.pojo.po.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <br>
 * <b>功能：</b><br>

 * <b>日期：</b>2017/6/19 17:10<br>
 *
 * @author Kael
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>, JpaSpecificationExecutor<UserModel> {

    /**
     * 根据手机号码查询未删除的用户
     *
     * @param userName 手机号码
     * @param deleted  删除状态
     * @return 用户
     */
    UserModel findByPhoneNumberAndDeleted(String userName, Integer deleted);

}
