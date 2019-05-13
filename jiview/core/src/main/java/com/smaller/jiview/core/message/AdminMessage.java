package com.smaller.jiview.core.message;

/**
 * Created by xiagf on 2019/05/06.
 */
public class AdminMessage {
    private AdminMessage() {
    }

    /**
     * ADMIN端业务信息代码格式：12CCCCD
     * 第1位：固定为1,代表业务信息
     * 第2位：固定为2,代表Admin端
     * 第3~6位：顺序增长,代表不同的业务
     * 第7位：0.成功信息,1.一种异常信息,2.另一种异常信息,3.第三种异常信息,以此类推
     */
    public static final BaseMessage LOGIN_SUCCESS = new BaseMessage("1200010", "登录成功");
    public static final BaseMessage LOGIN_FAILED = new BaseMessage("1200011", "登录失败");
    public static final BaseMessage NO_SUCH_USER = new BaseMessage("1200021", "用户不存在");
    public static final BaseMessage SYS_USER_EXSITS = new BaseMessage("1200031", "账号已存在");

}
