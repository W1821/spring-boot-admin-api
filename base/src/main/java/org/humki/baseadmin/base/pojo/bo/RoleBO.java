package org.humki.baseadmin.base.pojo.bo;

import com.google.common.collect.Sets;
import lombok.*;
import org.humki.baseadmin.base.pojo.dto.role.RoleDTO;
import org.humki.baseadmin.base.pojo.dto.role.RoleSearchDTO;
import org.humki.baseadmin.base.pojo.po.ButtonModel;
import org.humki.baseadmin.base.pojo.po.MenuModel;
import org.humki.baseadmin.base.pojo.po.RoleModel;
import org.humki.baseadmin.base.repository.ButtonRepository;
import org.humki.baseadmin.base.repository.MenuRepository;
import org.humki.baseadmin.base.repository.RoleRepository;
import org.humki.baseadmin.common.constant.GlobalEnum;
import org.humki.baseadmin.common.pojo.bo.BaseBO;
import org.humki.baseadmin.common.pojo.dto.base.message.EmptyData;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ExceptionUtil;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.humki.baseadmin.common.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kael
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class RoleBO extends BaseBO<RoleDTO> {

    private RoleRepository repository;
    private MenuRepository menuRepository;
    private ButtonRepository buttonRepository;
    private RoleDTO dto;
    private RoleModel model;

    @Override
    public ResponseMessage<RoleDTO> queryOne() {
        findById();
        poToDto();
        return ResponseMessageUtil.success(dto);
    }

    @Override
    public ResponseMessage<EmptyData> save() {
        if (dto.getId() == null) {
            // 新增
            dtoToPo();
        } else {
            update();
        }
        // 更新角色和菜单的关系
        setMenusAndButtons();
        // 保存数据库
        repository.save(model);
        return ResponseMessageUtil.saveSuccess();
    }

    @Override
    public ResponseMessage<EmptyData> delete() {
        findById();
        logicDelete();
        return ResponseMessageUtil.deleteSuccess();
    }

    @Override
    public RoleDTO poToDto() {
        if (dto == null) {
            dto = RoleDTO.builder().build();
        }
        dto.setId(model.getId());
        dto.setRoleName(model.getRoleName());
        dto.setDescription(model.getDescription());
        dto.setRoleStatus(String.valueOf(model.getRoleStatus()));
        dto.setCreateTime(model.getCreateTime());
        dto.setMenuIds(model.getMenus().stream().map(MenuModel::getId).collect(Collectors.toList()));
        dto.setButtonIds(model.getButtons().stream().map(ButtonModel::getId).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public void dtoToPo() {
        if (model == null) {
            model = new RoleModel();
        }
        model.setId(dto.getId());
        model.setRoleName(dto.getRoleName());
        model.setDescription(dto.getDescription());
        model.setRoleStatus(Integer.valueOf(dto.getRoleStatus()));
        // 默认未删除
        model.setDeleted(GlobalEnum.DELETED.NO.getKey());
        model.setCreateTime(LocalDateTime.now());
    }

    /**
     * 创建JPA动态查询条件
     */
    public static Specification<RoleModel> createSpecification(RoleSearchDTO dto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtil.isNotEmpty(dto.getRoleName())) {
                predicates.add(cb.like(root.get("roleName"), dto.getRoleName() + "%"));
            }
            if (StringUtil.isNotEmpty(dto.getRoleStatus())) {
                predicates.add(cb.equal(root.get("roleStatus"), dto.getRoleStatus()));
            }
            predicates.add(cb.equal(root.get("deleted"), GlobalEnum.DELETED.NO.getKey()));
            Predicate[] array = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(array));
        };
    }

    /**
     * 判断是否删除
     *
     * @return true:已删除，false:未删除
     */
    public boolean unDeleted() {
        return model.getDeleted() != null && model.getDeleted().equals(GlobalEnum.DELETED.NO.getKey());
    }

    /* ====================================================================================================== */

    /**
     * 根据主键查询
     */
    private void findById() {
        model = repository.findById(dto.getId()).orElse(null);
        if (model == null) {
            ExceptionUtil.throwIdError();
        }
    }

    /**
     * 逻辑删除
     */
    private void logicDelete() {
        model.setDeleted(GlobalEnum.DELETED.YES.getKey());
        model.setUpdateTime(LocalDateTime.now());
        repository.save(model);
    }

    /**
     * 修改
     */
    private void update() {
        findById();
        model.setRoleName(dto.getRoleName());
        model.setRoleStatus(Integer.valueOf(dto.getRoleStatus()));
        model.setDescription(dto.getDescription());
        model.setUpdateTime(LocalDateTime.now());
    }

    /**
     * 更新角色和菜单的关系
     */
    private void setMenusAndButtons() {
        if (dto.getMenuIds() == null || dto.getMenuIds().isEmpty()) {
            return;
        }
        List<MenuModel> menus = menuRepository.findByIdIn(dto.getMenuIds());
        model.setMenus(Sets.newHashSet(menus));
        if (dto.getButtonIds() == null || dto.getButtonIds().isEmpty()) {
            return;
        }
        List<ButtonModel> buttons = buttonRepository.findByIdIn(dto.getButtonIds());
        model.setButtons(Sets.newHashSet(buttons));
    }

}
