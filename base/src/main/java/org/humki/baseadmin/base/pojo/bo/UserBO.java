package org.humki.baseadmin.base.pojo.bo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.humki.baseadmin.common.pojo.bo.BaseBO;
import org.humki.baseadmin.common.config.AdminConfig;
import org.humki.baseadmin.common.constant.GlobalEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ExceptionUtil;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.humki.baseadmin.base.constant.UserEnum;
import org.humki.baseadmin.base.pojo.dto.role.BaseRoleDTO;
import org.humki.baseadmin.base.pojo.dto.user.UserDTO;
import org.humki.baseadmin.base.pojo.dto.user.UserSearchDTO;
import org.humki.baseadmin.base.pojo.po.RoleModel;
import org.humki.baseadmin.base.pojo.po.UserModel;
import org.humki.baseadmin.base.repository.RoleRepository;
import org.humki.baseadmin.base.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kael
 */
@AllArgsConstructor
@Getter
@ToString
@Builder
public class UserBO extends BaseBO {

    private AdminConfig adminConfig;

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    private UserDTO dto;
    private UserModel model;

    @Override
    public ResponseMessage queryOne() {
        findById();
        poToDto();
        dto.setPassword(null);
        return ResponseMessageUtil.success(dto);
    }

    @Override
    public ResponseMessage save() {
        if (dto.getId() == null) {
            // 新增
            dtoToPo();
            // 判断是否使用默认密码
            if (StringUtils.isEmpty(model.getPassword())) {
                model.setPassword(passwordEncoder.encode(adminConfig.getDefaultPassword()));
            }
        } else {
            update();
        }
        if (dto.getRoles() != null) {
            // 更新人员和角色的关系
            List<Long> roleIds = dto.getRoles().stream().map(BaseRoleDTO::getId).collect(Collectors.toList());
            List<RoleModel> roles = roleRepository.findByIdIn(roleIds);
            model.setRoles(Sets.newHashSet(roles));
        }
        // 保存数据库
        repository.save(model);
        return ResponseMessageUtil.saveSuccess();
    }

    @Override
    public ResponseMessage delete() {
        findById();
        logicDelete();
        return ResponseMessageUtil.deleteSuccess();
    }

    @Override
    public UserDTO poToDto() {
        if (dto == null) {
            dto = UserDTO.builder().build();
        }
        dto.setId(model.getId());
        dto.setPhoneNumber(model.getPhoneNumber());
        // 密码不返回到前端
        dto.setPassword(null);
        dto.setUserName(model.getUserName());
        dto.setHeadPictureUrl(model.getHeadPictureUrl());
        dto.setAccountStatus(String.valueOf(model.getAccountStatus()));
        dto.setCreateTime(model.getCreateTime());
        // 用户角色信息
        List<BaseRoleDTO> roles = model.getRoles().stream()
                .filter(e -> RoleBO.builder().model(e).build().unDeleted())
                .map(e -> BaseRoleDTO.builder().id(e.getId()).roleName(e.getRoleName()).build())
                .collect(Collectors.toList());
        dto.setRoles(roles);
        return dto;
    }

    @Override
    public UserModel dtoToPo() {
        if (model == null) {
            model = new UserModel();
        }
        model.setId(dto.getId());
        // 基本信息
        model.setUserName(dto.getUserName());
        if (!StringUtils.isEmpty(dto.getPassword())) {
            model.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        model.setPhoneNumber(dto.getPhoneNumber());
        model.setHeadPictureUrl(dto.getHeadPictureUrl());
        // 状态信息
        model.setAccountStatus(GlobalEnum.DISABLED.NO.getKey());
        model.setSuperAdmin(UserEnum.SUPER_ADMIN.NO.getKey());
        model.setDeleted(GlobalEnum.DELETED.NO.getKey());
        // 创建时间
        model.setCreateTime(new Date());
        return model;
    }


    /**
     * 创建JPA动态查询条件
     */
    public static Specification<UserModel> createSpecification(UserSearchDTO dto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (!StringUtils.isEmpty(dto.getUserName())) {
                predicates.add(cb.like(root.get("userName"), dto.getUserName() + "%"));
            }
            if (!StringUtils.isEmpty(dto.getPhoneNumber())) {
                predicates.add(cb.like(root.get("phoneNumber"), dto.getPhoneNumber() + "%"));
            }
            predicates.add(cb.equal(root.get("deleted"), GlobalEnum.DELETED.NO.getKey()));
            Predicate[] array = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(array));
        };
    }

    /**
     * 判断是否是超级管理员
     */
    public boolean userIsSuperAdmin() {
        return model.getSuperAdmin() != null && model.getSuperAdmin().equals(UserEnum.SUPER_ADMIN.YES.getKey());
    }

    /* ====================================================================================================== */

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
        model.setUpdateTime(new Date());
        repository.save(model);
    }

    /**
     * 更新
     */
    private void update() {
        findById();
        model.setUserName(dto.getUserName());
        if (!StringUtils.isEmpty(dto.getPassword())) {
            model.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        model.setPhoneNumber(dto.getPhoneNumber());
        model.setHeadPictureUrl(dto.getHeadPictureUrl());
        model.setAccountStatus(Integer.valueOf(dto.getAccountStatus()));
        model.setUpdateTime(new Date());
    }

}
