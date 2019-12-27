package org.humki.baseadmin.common.pojo.dto.base.page;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.humki.baseadmin.common.pojo.dto.BaseDTO;

/**
 * <br>
 * <b>功能：</b>分页查询DTO<br>

 * <b>日期：</b>2017/4/11 14:49<br>
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel("分页排序")
public class PageDTO extends BaseDTO {

    /**
     * 第几页，从1开始。
     * 默认为第1页
     */
    @ApiModelProperty(value = "第几页，从1开始。默认为第1页", example = "1")
    private Integer index;
    /**
     * 页面容量，一页多少条。
     * 默认为10条
     */
    @ApiModelProperty(value = "页面容量，一页多少条。默认为10条", example = "1")
    private Integer size;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段", example = "name")
    private String sortField;

    /**
     * 排序方式
     */
    @ApiModelProperty(value = "排序方式", example = "desc")
    private String sortOrder;

    public Integer getIndex() {
        // 分页从0开始
        return index == null || index <= 0 ? 0 : index - 1;
    }

    public Integer getSize() {
        return size == null || size < 1 || size > 100 ? 10 : size;
    }
}
