package com.smaller.jiview.moduler.system.service.impl;

import com.smaller.jiview.moduler.system.manager.SysUserManager;
import com.smaller.jiview.moduler.system.platform.system.mapper.SysUserMapper;
import com.smaller.jiview.moduler.system.platform.system.model.SysUser;
import com.smaller.jiview.moduler.system.pojo.param.LoginParam;
import com.smaller.jiview.moduler.system.pojo.param.ResetPwdParam;
import com.smaller.jiview.moduler.system.service.LoginService;
import com.smaller.jiview.core.config.security.JwtHelper;
import com.smaller.jiview.core.exception.BizException;
import com.smaller.jiview.core.message.AdminMessage;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.JwtDTO;
import com.smaller.jiview.core.pojo.dto.UserForReturnDTO;
import com.smaller.jiview.core.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xigf 2019/05/23
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private SysUserManager sysUserManager;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResultBO<UserForReturnDTO> login(LoginParam loginParam, HttpServletRequest request) {
        ResultBO<UserForReturnDTO> bo = new ResultBO<>();

        String userLogin = loginParam.getAccount();
        String userPwd = loginParam.getUserPwd();

        SysUser sysUser = sysUserManager.getForAdminLogin(userLogin, userPwd);
        Long userId = sysUser.getId();

        UserForReturnDTO userForReturnDTO = new UserForReturnDTO();
        JwtDTO jwtDTO = new JwtDTO();

        // 存入token的用户信息项目，在此手动设置
        jwtDTO.setLoginPkid(userId);
        jwtDTO.setUserLogin(userLogin);

        // 需要返回的用户信息项目，在此手动设置
        userForReturnDTO.setUserId(userId);
        userForReturnDTO.setDeptId(sysUser.getDeptId());
        userForReturnDTO.setAccount(userLogin);
        userForReturnDTO.setUserName(sysUser.getUserName());
        userForReturnDTO.setAuthorization(jwtHelper.createAndSaveToken(jwtDTO));

        bo.setRow(userForReturnDTO);
        return bo;
    }

    /**
     * 设置密码
     *
     * @param resetPwdParam
     * @return
     */
    @Override
    public ResultBO resetPwd(ResetPwdParam resetPwdParam) {
        ResultBO bo = new ResultBO();
        SysUser sysUser = sysUserManager.getByUserLogin(resetPwdParam.getAccount());
        if (ObjectUtils.isEmpty(sysUser)){
            throw new BizException(AdminMessage.NO_SUCH_USER);
        }

        SysUser sysUserForUpdate = new SysUser();
        sysUserForUpdate.setAccount(resetPwdParam.getAccount());
        sysUserForUpdate.setPassword(resetPwdParam.getPassword());

        String encodePwd = SecurityUtil.encodePwd(sysUserForUpdate.getPassword());

        sysUserForUpdate.setPassword(encodePwd);


        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("account", sysUserForUpdate.getAccount());
        bo.setOpResult(sysUserMapper.updateByExampleSelective(sysUserForUpdate, example));

        return bo;
    }
}
