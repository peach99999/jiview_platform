package com.smaller.jiview.admin.service.impl;

import com.smaller.jiview.admin.manager.SysUserManager;
import com.smaller.jiview.admin.platform.system.mapper.SysUserMapper;
import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.admin.pojo.param.LoginParam;
import com.smaller.jiview.admin.pojo.param.ResetPwdParam;
import com.smaller.jiview.admin.service.LoginService;
import com.smaller.jiview.core.config.security.JwtHelper;
import com.smaller.jiview.core.exception.BizException;
import com.smaller.jiview.core.message.AdminMessage;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.JwtDTO;
import com.smaller.jiview.core.pojo.dto.UserForReturnDTO;
import com.smaller.jiview.core.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        SysUser orgUser = new SysUser();

        orgUser.setAccount(resetPwdParam.getAccount());
        orgUser.setPassword(resetPwdParam.getPassword());

        String encodePwd = SecurityUtil.encodePwd(orgUser.getPassword());

        orgUser.setPassword(encodePwd);

        if (sysUserManager.getByUserLogin(orgUser.getAccount()) == null) {
            throw new BizException(AdminMessage.NO_SUCH_USER);
        }

        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("account", orgUser.getAccount());
        bo.setOpResult(sysUserMapper.updateByExampleSelective(orgUser, example));

        return bo;
    }
}
