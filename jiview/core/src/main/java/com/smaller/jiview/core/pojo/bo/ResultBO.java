package com.smaller.jiview.core.pojo.bo;

import com.smaller.jiview.core.constant.Constants;
import com.smaller.jiview.core.message.BaseMessage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuyuqing on 2017/04/18.
 */
@Data
public class ResultBO<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -7727710843787001606L;

    private List<T> rows;
    private T row;
    private Long count;
    private Integer pageNo;
    private Integer pageSize;
    private String msgCode;
    private String msg;
    private Integer opResult = Constants.OP_RESULT_FAILED;

    public void setBaseMsgCode(BaseMessage msgCode) {
        this.msgCode = msgCode.getMsgCode();
        this.msg = msgCode.getMsg();
    }
}
