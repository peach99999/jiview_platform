package com.smaller.jiview.moduler.system.pojo.model.ext;

import com.smaller.jiview.moduler.system.platform.system.model.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
@Data
public class SysMenuExt extends SysMenu {
    private String routerName;

    private Byte authorizeLevel;

    private List<SysMenuExt> children;

    public List<SysMenuExt> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuExt> children) {
        this.children = children;
    }
}
