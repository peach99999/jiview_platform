package com.smaller.jiview.admin.pojo.model.ext;

import com.smaller.jiview.admin.platform.system.model.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class SysMenuExt extends SysMenu {
    private String routerName;

    private List<SysMenuExt> children;

    public List<SysMenuExt> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuExt> children) {
        this.children = children;
    }
}
