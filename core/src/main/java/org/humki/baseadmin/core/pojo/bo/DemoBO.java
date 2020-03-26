package org.humki.baseadmin.core.pojo.bo;

import lombok.*;
import org.humki.baseadmin.core.pojo.po.DemoModel;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DemoBO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 批量操作
     */
    public void batchSave(List<DemoModel> list) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(list);
        namedParameterJdbcTemplate.batchUpdate(getBatchUpdateSql(), batch);
    }

    private  String getBatchUpdateSql() {
        return "REPLACE INTO demo(" +
                "id, " +
                "demo_name, " +
                "create_time, " +
                "update_time " +
                ") " +
                "VALUES (" +
                ":id, " +
                ":demoName, " +
                ":createTime, " +
                ":updateTime " +
                ")";
    }

}
