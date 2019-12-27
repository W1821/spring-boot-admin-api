package org.humki.baseadmin.common.service;

import org.apache.commons.lang3.StringUtils;
import org.humki.baseadmin.common.pojo.dto.base.page.PageDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


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
        // 如果有排序
        if (!StringUtils.isEmpty(dto.getSortField())) {
            boolean isDesc = !StringUtils.isEmpty(dto.getSortOrder()) && dto.getSortOrder().contains("desc");
            return isDesc ? getPageableDesc(dto) : getPageableAsc(dto);
        }
        return PageRequest.of(dto.getIndex(), dto.getSize());
    }


    /**
     * 多条件分页排序
     */
    protected Pageable getPageable(Integer page, Integer size, Sort.Direction direction, String... sortFields) {
        Sort sort = Sort.by(direction, sortFields);
        return PageRequest.of(page, size, sort);
    }

    /* ======================================= private method========================================== */

    /**
     * 获取分页查询对象
     */
    private Pageable getPageableAsc(PageDTO dto) {
        Integer page = dto.getIndex();
        Integer size = dto.getSize();
        Sort sort = Sort.by(Sort.Direction.ASC, dto.getSortField());
        return PageRequest.of(page, size, sort);
    }

    /**
     * 获取分页查询对象
     */
    private Pageable getPageableDesc(PageDTO dto) {
        Integer page = dto.getIndex();
        Integer size = dto.getSize();
        Sort sort = Sort.by(Sort.Direction.DESC, dto.getSortField());
        return PageRequest.of(page, size, sort);
    }

}
