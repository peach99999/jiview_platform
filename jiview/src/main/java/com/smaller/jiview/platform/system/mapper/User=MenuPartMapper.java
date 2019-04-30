package com.smaller.jiview.platform.system.mapper;

import com.smaller.jiview.platform.system.model.User=MenuPart;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface User=MenuPartMapper extends Mapper<User=MenuPart>, MySqlMapper<User=MenuPart> {
}