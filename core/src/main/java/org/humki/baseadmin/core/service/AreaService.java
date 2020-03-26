package org.humki.baseadmin.core.service;

import org.humki.baseadmin.base.service.BaseBaseService;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.humki.baseadmin.core.pojo.po.AreaModel;
import org.humki.baseadmin.core.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kael
 */
@Service
public class AreaService extends BaseBaseService {

    private final AreaRepository areaRepository;

    @Autowired
    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    /**
     * 保存
     */
    public ResponseMessage save() {
        AreaModel model = new AreaModel();
        model.setAreaName("p");
        areaRepository.save(model);
        return ResponseMessageUtil.success();
    }

}
