package com.smaller.jiview.admin.service.impl;

import com.smaller.jiview.admin.manager.UserManager;
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

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserManager userManager;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResultBO<UserForReturnDTO> login(LoginParam loginParam, HttpServletRequest request) {
        ResultBO<UserForReturnDTO> bo = new ResultBO<>();

        String userLogin = loginParam.getUserLogin();
        String userPwd = loginParam.getUserPwd();

        SysUser SysUser = userManager.getForAdminLogin(userLogin, userPwd);
        Long userId = SysUser.getUserId();

        UserForReturnDTO userForReturnDTO = new UserForReturnDTO();
        JwtDTO jwtDTO = new JwtDTO();

//        LoginUserDTO loginUserDTO = new LoginUserDTO();
//        loginUserDTO.setLoginUserPkid(loginPkid);
//        loginUserDTO.setLoginUserUuid(loginUUID);
//        loginUserDTO.setIp(HttpUtil.getClientRealIP(request));
//        loginUserDTO.setRequestUri(request.getRequestURI());

        // 存入token的用户信息项目，在此手动设置
        jwtDTO.setLoginPkid(userId);
//        jwtDTO.setLoginUUID(loginUUID);
        jwtDTO.setUserLogin(userLogin);

        // 需要返回的用户信息项目，在此手动设置
        userForReturnDTO.setLoginPkid(userId);
//        userForReturnDTO.setLoginUUID(loginUUID);
        userForReturnDTO.setUserLogin(userLogin);
        userForReturnDTO.setAuthorization(jwtHelper.createAndSaveToken(jwtDTO));

        bo.setRow(userForReturnDTO);
        return bo;
    }

    /**
     * 设置密码
     * @param resetPwdParam
     * @return
     */
    @Override
    public ResultBO resetPwd(ResetPwdParam resetPwdParam) {
        ResultBO bo = new ResultBO();
        SysUser orgUser = new SysUser();

        orgUser.setAccount(resetPwdParam.getLogin());
        orgUser.setPassword(resetPwdParam.getPassword());

        String encodePwd = SecurityUtil.encodePwd(orgUser.getPassword());

        orgUser.setPassword(encodePwd);

        if (userManager.getByUserLogin(orgUser.getAccount()) == null) {
            throw new BizException(AdminMessage.NO_SUCH_USER);
        }

        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("account",orgUser.getAccount());
        bo.setOpResult(sysUserMapper.updateByExampleSelective(orgUser, example));

        return bo;
    }
}
