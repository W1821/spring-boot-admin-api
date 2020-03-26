package org.humki.baseadmin.base.pojo.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.humki.baseadmin.base.pojo.dto.menu.ButtonDTO;
import org.humki.baseadmin.base.pojo.dto.menu.MenuDTO;
import org.humki.baseadmin.base.pojo.po.ButtonModel;
import org.humki.baseadmin.base.pojo.po.MenuModel;
import org.humki.baseadmin.base.repository.MenuRepository;
import org.humki.baseadmin.common.constant.GlobalEnum;
import org.humki.baseadmin.common.pojo.bo.BaseBO;
import org.humki.baseadmin.common.pojo.dto.base.message.EmptyData;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ExceptionUtil;
import org.humki.baseadmin.common.util.ResponseMessageUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单
 *
 * @author Kael
 */
@Getter
@ToString
@Builder
public class MenuBO extends BaseBO<MenuDTO> {

    private MenuRepository repository;
    private MenuDTO dto;
    private MenuModel model;

    @Override
    public ResponseMessage<MenuDTO> queryOne() {
        findById();
        poToDto();
        // 关联按钮项
        setButtons();
        return ResponseMessageUtil.success(dto);
    }

    @Override
    public ResponseMessage<EmptyData> save() {
        if (dto.getId() == null) {
            // 新增
            dtoToPo();
            model.setCreateTime(LocalDateTime.now());
            model.setDeleted(GlobalEnum.DELETED.NO.getKey());
        } else {
            update();
        }

        List<ButtonDTO> buttons = dto.getButtons();
        if (buttons != null && !buttons.isEmpty()) {
            Set<ButtonModel> newButtonModels = buttons.stream().map(this::createButtonModel).collect(Collectors.toSet());

            // 关联按钮
            Set<ButtonModel> buttonModels = model.getButtons();
            if (buttonModels != null) {
                buttonModels.clear();
                buttonModels.addAll(newButtonModels);
            } else {
                model.setButtons(newButtonModels);
            }
        }

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
    public MenuDTO poToDto() {
        if (dto == null) {
            dto = MenuDTO.builder().build();
        }
        dto.setId(model.getId());
        dto.setPid(model.getPid());
        dto.setPids(model.getPids());
        dto.setRoutePath(model.getRoutePath());
        dto.setMenuName(model.getMenuName());
        dto.setIcon(model.getIcon());
        dto.setRank(model.getRank());
        dto.setActions(model.getActions());
        return dto;
    }

    @Override
    public void dtoToPo() {
        model = new MenuModel();
        model.setId(dto.getId());
        setModelByDto();
    }

    public MenuDTO poToDtoHaveButtons() {
        poToDto();
        // 关联按钮项
        setButtons();
        return dto;
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
     * 设置配置项
     */
    private void setButtons() {
        List<ButtonDTO> buttons = model.getButtons().stream()
                .map(this::createButtonDTO)
                .collect(Collectors.toList());
        dto.setButtons(buttons);
    }

    /**
     * 更新
     */
    private void update() {
        findById();
        setModelByDto();
        model.setUpdateTime(LocalDateTime.now());
    }

    private void setModelByDto() {
        model.setPid(dto.getPid());
        model.setPids(dto.getPids());
        model.setMenuName(dto.getMenuName());
        model.setRoutePath(dto.getRoutePath());
        model.setIcon(dto.getIcon());
        model.setRank(dto.getRank());
        model.setActions(dto.getActions());
    }

    /**
     * 创建按钮数据库对象
     */
    private ButtonModel createButtonModel(ButtonDTO button) {
        ButtonModel buttonModel = new ButtonModel();
        buttonModel.setId(button.getId());
        buttonModel.setName(button.getButtonName());
        buttonModel.setCode(button.getCode());
        buttonModel.setActions(button.getActions());
        buttonModel.setRoutePath(button.getRoutePath());
        return buttonModel;
    }

    /**
     * 创建按钮对象
     */
    private ButtonDTO createButtonDTO(ButtonModel button) {
        ButtonDTO buttonDTO = new ButtonDTO();
        buttonDTO.setId(button.getId());
        buttonDTO.setButtonName(button.getName());
        buttonDTO.setCode(button.getCode());
        buttonDTO.setActions(button.getActions());
        buttonDTO.setRoutePath(button.getRoutePath());
        return buttonDTO;
    }

}
