package org.humki.baseadmin.security.service;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.humki.baseadmin.base.pojo.po.RoleModel;
import org.humki.baseadmin.base.pojo.po.UserModel;
import org.humki.baseadmin.base.util.UserDetail;
import org.humki.baseadmin.base.service.MenuService;
import org.humki.baseadmin.base.service.UserService;
import org.humki.baseadmin.common.constant.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br>
 * <b>功能：</b>spring security 鉴权，重写loadUserByUsername方法，通过帐号获取帐号id、码、是否可用等信息<br>

 * <b>日期：</b>2017/10/4 23:27<br>
 *
 * @author Kael
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final MenuService menuService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService, MenuService menuService) {
        this.userService = userService;
        this.menuService = menuService;
    }

    /**
     * 查询登录信息是否正确
     *
     * @param userName 手机号码
     */
    @Override
    public UserDetails loadUserByUsername(String userName) {
        log.debug("--------------系统登录鉴权开始---------------");

        UserModel user = userService.findByPhoneNumber(userName);
        if (user == null) {
            throw new UsernameNotFoundException("系统中不存在该用户。");
        }

        // 设置帐号角色
        Set<GrantedAuthority> authSet = getAuthorities(user);

        UserDetail userDetail = new UserDetail(user, authSet);
        List<String> menus = menuService.getMenuActionList(user);

        // 当前用户的所有菜单url
        List<String[]> pathVariableArray = getPathVariable(menus);
        userDetail.setPathVariable(pathVariableArray);
        userDetail.setPathInvariable(menus);

        log.debug("--------------系统登录鉴权结束---------------");
        return userDetail;
    }

    /**
     * 账号角色
     */
    private Set<GrantedAuthority> getAuthorities(UserModel user) {
        Set<RoleModel> roles = user.getRoles();
        if (roles != null) {
            return roles.stream()
                    .map(roleModel -> (GrantedAuthority) new SimpleGrantedAuthority(roleModel.getRoleName()))
                    .collect(Collectors.toSet());
        }
        return Sets.newHashSet();
    }

    private List<String[]> getPathVariable(List<String> menus) {
        List<String> pathVariable = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).contains(GlobalConstant.OPEN_BRACE) && menus.get(i).contains(GlobalConstant.CLOSE_BRACE)) {
                pathVariable.add(menus.get(i));
                menus.remove(i);
                i--;
            }
        }
        List<String[]> pathVariableArray = new ArrayList<>();
        for (String path : pathVariable) {
            if (path.startsWith(GlobalConstant.LEFT_SLASH)) {
                path = path.replaceFirst(GlobalConstant.LEFT_SLASH, GlobalConstant.EMPTY_STRING);
            }
            pathVariableArray.add(path.split(GlobalConstant.LEFT_SLASH));
        }
        return pathVariableArray;
    }

}
