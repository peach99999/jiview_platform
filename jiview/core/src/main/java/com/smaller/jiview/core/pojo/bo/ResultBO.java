package com.smaller.jiview.core.pojo.bo;

import com.github.pagehelper.Page;
import com.smaller.jiview.core.constant.Constants;
import com.smaller.jiview.core.message.BaseMessage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiagf on 2019/05/08.
 */
@Data
public class ResultBO<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -7727710843787001606L;

    private List<T> rows;
    private T row;
    private Long count;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPage;
    private Integer startIndex;
    private String msgCode;
    private String msg;
    private Integer opResult = Constants.OP_RESULT_FAILED;

    public void setBaseMsgCode(BaseMessage msgCode) {
        this.msgCode = msgCode.getMsgCode();
        this.msg = msgCode.getMsg();
    }

    public ResultBO(List<T> rows) {
        init(rows);
    }
    public ResultBO(){
        super();
    }

    /**
     * 初始化
     *
     * @param rows
     */
    private void init(List<T> rows) {
        if (rows instanceof Page) {
            Page<T> page = (Page<T>) rows;
            this.count = page.getTotal();
            this.rows = page.getResult();
            this.pageNo = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.totalPage = page.getPages();
        }
    }
}
