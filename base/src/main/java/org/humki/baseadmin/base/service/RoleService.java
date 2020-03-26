package org.humki.baseadmin.base.service;

import com.google.common.collect.Lists;

import org.humki.baseadmin.base.pojo.bo.RoleBO;
import org.humki.baseadmin.base.pojo.bo.UserBO;
import org.humki.baseadmin.base.pojo.dto.role.RoleDTO;
import org.humki.baseadmin.base.pojo.dto.role.RoleSearchDTO;
import org.humki.baseadmin.base.pojo.po.RoleModel;
import org.humki.baseadmin.base.pojo.po.UserModel;
import org.humki.baseadmin.base.repository.ButtonRepository;
import org.humki.baseadmin.base.repository.MenuRepository;
import org.humki.baseadmin.base.repository.RoleRepository;
import org.humki.baseadmin.common.constant.GlobalEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.EmptyData;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kael
 */
@Service
public class RoleService extends BaseBaseService {

    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;
    private final ButtonRepository buttonRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, MenuRepository menuRepository, ButtonRepository buttonRepository) {
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
        this.buttonRepository = buttonRepository;
    }

    /**
     * 查询所有
     */
    public ResponseMessage<Page<RoleDTO>> list(RoleSearchDTO dto) {
        // 分页查询
        Page<RoleModel> dataList = roleRepository.findAll(RoleBO.createSpecification(dto), getPageable(dto));
        // 处理数据返回到前端
        Page<RoleDTO> page = dataList.map(this::poToDto);
        return ResponseMessageUtil.success(page);
    }

    /**
     * 查询一个
     */
    public ResponseMessage<RoleDTO> query(Long id) {
        RoleBO bo = RoleBO.builder().repository(roleRepository).dto(RoleDTO.builder().id(id).build()).build();
        return bo.queryOne();
    }

    /**
     * 保存
     */
    public ResponseMessage<EmptyData> save(RoleDTO dto) {
        RoleBO bo = RoleBO.builder()
                .repository(roleRepository)
                .menuRepository(menuRepository)
                .buttonRepository(buttonRepository)
                .dto(dto)
                .build();
        return bo.save();
    }

    /**
     * 删除
     */
    public ResponseMessage<EmptyData> delete(Long id) {
        RoleBO bo = RoleBO.builder().repository(roleRepository).dto(RoleDTO.builder().id(id).build()).build();
        return bo.delete();
    }


    /**
     * 当前登陆人的角色
     */
    public ResponseMessage<List<RoleDTO>> getRoleList() {

        // 当前登陆人
        UserModel model = getUserDetail();

        List<RoleModel> roleList;
        UserBO userBO = UserBO.builder().model(model).build();
        // 如果是超级管理员,查询所有角色
        if (userBO.userIsSuperAdmin()) {
            roleList = roleRepository.findByDeleted(GlobalEnum.DELETED.NO.getKey());
        } else {
            roleList = Lists.newArrayList(model.getRoles());
        }
        // 返回DTO
        List<RoleDTO> roleDTOList = roleList.stream().map(this::poToDto).collect(Collectors.toList());
        return ResponseMessageUtil.success(roleDTOList);
    }

    /**
     * 数据库对象转dto对象
     */
    private RoleDTO poToDto(RoleModel model) {
        return RoleBO.builder().model(model).build().poToDto();
    }

}
