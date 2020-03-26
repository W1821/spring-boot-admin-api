package org.humki.baseadmin.common.util;

import org.humki.baseadmin.common.pojo.dto.base.page.PageDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;


/**
 * jpa分页查询工具
 */
public class JPAPageableUtil {

    private final static String DESC = "desc";

    /**
     * 获取分页查询对象
     */
    public static Pageable getPageable(PageDTO dto) {
        // 第几页
        Integer index = dto.getIndex();
        // 总页数
        Integer size = dto.getSize();
        // 排序字段，支持一个字段排序
        String sortField = dto.getSortField();
        // 如果有排序
        if (needSort(sortField)) {
            return isDesc(dto) ?
                    PageRequest.of(index, size, Sort.by(Sort.Direction.DESC, sortField))
                    :
                    PageRequest.of(index, size, Sort.by(Sort.Direction.ASC, sortField));
        }
        return PageRequest.of(index, size);
    }

    /* ======================================= private method========================================== */

    /**
     * 判断是否需要排序
     */
    private static boolean needSort(String sortField) {
        return StringUtil.isNotEmpty(sortField);
    }

    /**
     * 判断是否是倒序
     */
    private static boolean isDesc(PageDTO dto) {
        if (StringUtil.isEmpty(dto.getSortOrder())) {
            return false;
        }
        return dto.getSortOrder().contains(DESC);
    }

}
