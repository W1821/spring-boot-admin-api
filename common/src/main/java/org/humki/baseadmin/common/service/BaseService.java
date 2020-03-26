package org.humki.baseadmin.common.service;

import org.humki.baseadmin.common.pojo.dto.base.page.PageDTO;
import org.humki.baseadmin.common.util.JPAPageableUtil;
import org.springframework.data.domain.Pageable;


/**
 * 基本的service
 *
 * @author Kael
 */
public class BaseService {

    /**
     * 获取分页查询对象
     */
    protected Pageable getPageable(PageDTO dto) {
        return JPAPageableUtil.getPageable(dto);
    }

}
