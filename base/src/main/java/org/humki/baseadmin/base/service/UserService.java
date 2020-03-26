package org.humki.baseadmin.base.service;

import org.humki.baseadmin.base.pojo.bo.UserBO;
import org.humki.baseadmin.base.pojo.dto.user.ModifyPwdDTO;
import org.humki.baseadmin.base.pojo.dto.user.UserDTO;
import org.humki.baseadmin.base.pojo.dto.user.UserSearchDTO;
import org.humki.baseadmin.base.pojo.po.UserModel;
import org.humki.baseadmin.base.repository.RoleRepository;
import org.humki.baseadmin.base.repository.UserRepository;
import org.humki.baseadmin.base.util.UserDetailUtil;
import org.humki.baseadmin.common.config.AdminConfig;
import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.constant.GlobalEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.EmptyData;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.humki.baseadmin.common.util.UploadFileUtil;
import org.humki.baseadmin.file.config.FileUploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户
 *
 * @author Kael
 */
@Service
public class UserService extends BaseBaseService {

    private final AdminConfig adminConfig;
    private final FileUploadConfig fileUploadConfig;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(
            AdminConfig adminConfig,
            FileUploadConfig fileUploadConfig,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.adminConfig = adminConfig;
        this.fileUploadConfig = fileUploadConfig;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    /**
     * 查询所有
     */
    public ResponseMessage<Page<UserDTO>> list(UserSearchDTO dto) {
        // 分页查询
        Page<UserModel> dataList = userRepository.findAll(UserBO.createSpecification(dto), getPageable(dto));
        // 处理数据返回到前端
        Page<UserDTO> pagination = dataList.map(this::poToDto);
        return ResponseMessageUtil.success(pagination);
    }

    /**
     * 查询一个
     */
    public ResponseMessage<UserDTO> query(Long id) {
        UserBO userBO = UserBO.builder().repository(userRepository).dto(UserDTO.builder().id(id).build()).build();
        return userBO.queryOne();
    }

    /**
     * 保存
     */
    public ResponseMessage<EmptyData> save(UserDTO dto) {
        // 判断手机号码是否有重复
        if (checkPhoneNumber(dto)) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1200);
        }
        // 判断是否有图片
        if (UploadFileUtil.checkIsBase64Image(dto.getHeadPictureBase64())) {
            String imageUrl = UploadFileUtil.writeImageBase64ToFile(dto.getHeadPictureBase64(), fileUploadConfig.getRootPath(), fileUploadConfig.getImageUrlPrefix());
            dto.setHeadPictureUrl(imageUrl);
        }
        UserBO userBO = UserBO.builder()
                .adminConfig(adminConfig)
                .repository(userRepository)
                .passwordEncoder(passwordEncoder)
                .roleRepository(roleRepository)
                .dto(dto)
                .build();
        return userBO.save();
    }

    /**
     * 删除
     */
    public ResponseMessage<EmptyData> delete(Long id) {
        UserBO userBO = UserBO.builder().repository(userRepository).dto(UserDTO.builder().id(id).build()).build();
        return userBO.delete();
    }


    /**
     * 检查手机号码是否存在
     */
    public ResponseMessage<EmptyData> checkNumber(String phoneNumber) {
        UserModel model = findByPhoneNumber(phoneNumber);
        if (model != null) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1200);
        }
        return ResponseMessageUtil.success();
    }

    /**
     * 修改密码
     */
    public ResponseMessage<EmptyData> modifyOwnPassword(ModifyPwdDTO dto) {
        UserModel user = UserDetailUtil.getCurrentUser();
        if (user == null) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_401);
        }
        if (!passwordEncoder.matches(dto.getOldPwd(), user.getPassword())) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1050);
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPwd()));
        userRepository.save(user);
        return ResponseMessageUtil.saveSuccess();
    }

    /**
     * 根据手机号查询
     */
    public UserModel findByPhoneNumber(String userName) {
        return userRepository.findByPhoneNumberAndDeleted(userName, GlobalEnum.DELETED.NO.getKey());
    }

    /* ================================================ 私有方法 =================================================*/

    /**
     * 判断手机号码是否有重复
     */
    private boolean checkPhoneNumber(UserDTO dto) {
        UserModel userModel = findByPhoneNumber(dto.getPhoneNumber());
        if (userModel == null) {
            return false;
        }
        // 新增
        if (dto.getId() == null) {
            return true;
        }
        // 修改
        return !userModel.getId().equals(dto.getId());

    }

    private UserDTO poToDto(UserModel model) {
        return UserBO.builder().model(model).build().poToDto();
    }


}
