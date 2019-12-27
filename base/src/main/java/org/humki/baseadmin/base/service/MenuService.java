package org.humki.baseadmin.base.service;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.humki.baseadmin.base.pojo.bo.MenuBO;
import org.humki.baseadmin.base.pojo.bo.RoleBO;
import org.humki.baseadmin.base.pojo.bo.UserBO;
import org.humki.baseadmin.base.pojo.dto.menu.MenuDTO;
import org.humki.baseadmin.base.pojo.po.ButtonModel;
import org.humki.baseadmin.base.pojo.po.MenuModel;
import org.humki.baseadmin.base.pojo.po.UserModel;
import org.humki.baseadmin.base.repository.MenuRepository;
import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.constant.GlobalConstant;
import org.humki.baseadmin.common.constant.GlobalEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单控制器
 *
 * @author Kael
 */
@Service
@Slf4j
public class MenuService extends SystemBaseService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * 查询所有
     */
    public ResponseMessage list() {
        List<MenuModel> menuList = menuRepository.findByDeletedOrderByRank(GlobalEnum.DELETED.NO.getKey());
        // 排序菜单
        List<MenuModel> sortedMenuList = getSortedMenuModels(menuList);
        // 返回DTO
        List<MenuDTO> dtoList = poListToDtoList(sortedMenuList);
        // 设置树桩展示名称
        dtoList.forEach(this::setShowName);
        return ResponseMessageUtil.success(dtoList);
    }

    /**
     * 查询一个
     */
    public ResponseMessage query(Long id) {
        MenuBO menuBO = MenuBO.builder()
                .repository(menuRepository)
                .dto(MenuDTO.builder().id(id).build())
                .build();
        return menuBO.queryOne();
    }

    /**
     * 保存
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage save(MenuDTO dto) {

        if (StringUtils.isNotEmpty(dto.getPids())) {
            String[] pids = dto.getPids().split(GlobalConstant.COMMA);
            switch (pids.length) {
                case 0:
                case 1:
                    dto.setButtons(null);
                    break;
                case 2:
                    // 只有第三极才能添加按钮
                    break;
                default:
                    // 判断级别，最多3级
                    return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1221);
            }
        }

        MenuBO menuBO = MenuBO.builder().repository(menuRepository).dto(dto).build();
        return menuBO.save();
    }

    /**
     * 删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage delete(Long id) {
        MenuBO bo = MenuBO.builder().repository(menuRepository).dto(MenuDTO.builder().id(id).build()).build();
        return bo.delete();
    }

    /**
     * 当前登陆人的菜单
     */
    public ResponseMessage getMenuList() {
        UserModel model = getUserDetail();
        List<MenuModel> menuList = getMenuModels(model);
        // 返回DTO
        List<MenuDTO> menuDTOList = poListToDtoList(menuList);
        return ResponseMessageUtil.success(menuDTOList);
    }

    /**
     * 当前登陆人的菜单
     */
    public List<String> getMenuActionList(UserModel model) {
        List<MenuModel> menuList = getMenuModels(model);
        Set<String> handlerUrls = Sets.newHashSet();
        menuList.forEach(m -> {
            // 增加菜单上的授权url
            if (m.getActions() != null) {
                Collections.addAll(handlerUrls, m.getActions().split(GlobalConstant.COMMA));
            }
            if (m.getButtons() == null) {
                return;
            }
            // 增加按钮上的授权url
            m.getButtons().forEach(b -> {
                if (b.getActions() != null) {
                    Collections.addAll(handlerUrls, b.getActions().split(GlobalConstant.COMMA));
                }
            });
        });

        return Lists.newArrayList(handlerUrls);
    }

    /* ================================================ 私有方法 =================================================*/

    /**
     * 返回DTO对象
     */
    private List<MenuDTO> poListToDtoList(List<MenuModel> sortedMenuList) {
        return sortedMenuList.stream().map(e -> MenuBO.builder().model(e).build().poToDtoHaveButtons()).collect(Collectors.toList());
    }


    /**
     * 用户的所有权限的菜单
     */
    private List<MenuModel> getMenuModels(UserModel model) {
        List<MenuModel> menuList;

        // 如果是超级管理员
        UserBO userBO = UserBO.builder().model(model).build();
        if (userBO.userIsSuperAdmin()) {
            menuList = menuRepository.findByDeletedOrderByRank(GlobalEnum.DELETED.NO.getKey());
        } else {
            // 用户角色关联的权限菜单
            Set<MenuModel> menus = Sets.newHashSet();
            // 用户角色关联的权限按钮
            Set<ButtonModel> buttons = Sets.newHashSet();
            model.getRoles().stream()
                    .filter(e -> RoleBO.builder().model(e).build().unDeleted())
                    .forEach(e -> {
                        menus.addAll(e.getMenus());
                        buttons.addAll(e.getButtons());
                    });
            // 上级菜单
            menus.addAll(getParentMenus(menus));
            menuList = Lists.newArrayList(menus);

            // 去掉没有权限的按钮
            menuList.forEach(m -> m.getButtons().retainAll(buttons));
        }
        return menuList;
    }


    /**
     * 上级菜单
     */
    private List<MenuModel> getParentMenus(Set<MenuModel> menuList) {
        Set<String> menuIds = Sets.newHashSet();
        menuList.stream()
                .filter(e -> e.getPids() != null)
                .forEach(e -> menuIds.addAll(Lists.newArrayList(e.getPids().split(GlobalConstant.COMMA))));
        List<Long> parentIds = menuIds.stream()
                .map(id -> {
                    try {
                        return Long.parseLong(id);
                    } catch (NumberFormatException ignored) {
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return menuRepository.findByIdIn(parentIds);
    }


    /**
     * 排序菜单
     */
    private List<MenuModel> getSortedMenuModels(List<MenuModel> menuList) {
        // 顶级菜单
        List<MenuModel> firstMenuList = menuList.stream()
                .filter(e -> e.getPid() == null)
                .collect(Collectors.toList());

        List<MenuModel> sortedMenuList = new ArrayList<>();
        firstMenuList.forEach(menuModel -> {
            sortedMenuList.add(menuModel);
            sortedMenuList.addAll(getAllChildrenMenu(menuList, menuModel));
        });
        return sortedMenuList;
    }

    /**
     * 递归出所有的下级菜单
     */
    private List<MenuModel> getAllChildrenMenu(List<MenuModel> menuList, MenuModel menuModel) {
        List<MenuModel> tempList = new ArrayList<>();
        for (MenuModel model : menuList) {
            if (menuModel.getId().equals(model.getPid())) {
                tempList.add(model);
                tempList.addAll(getAllChildrenMenu(menuList, model));
            }
        }
        return tempList;
    }

    private void setShowName(MenuDTO menuDTO) {
        int depth = getMenuDepth(menuDTO);
        if (depth == 0) {
            menuDTO.setShowName(menuDTO.getMenuName());
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("|——");
        }
        menuDTO.setShowName(sb.append(menuDTO.getMenuName()).toString());
    }

    private int getMenuDepth(MenuDTO menuDTO) {
        if (StringUtils.isEmpty(menuDTO.getPids())) {
            return 0;
        }
        return menuDTO.getPids().split(",").length;
    }

}
