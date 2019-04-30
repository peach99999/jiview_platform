package com.smaller.jiview.platform.system.mapper;

import com.smaller.jiview.platform.system.model.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
}