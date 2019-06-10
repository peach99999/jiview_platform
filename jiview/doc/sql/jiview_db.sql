/*
Navicat MySQL Data Transfer

Source Server         : eval
Source Server Version : 50516
Source Host           : 119.57.73.147:3306
Source Database       : jiview_db

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2019-04-30 09:31:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父部门ID',
  `dept_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '部门名称',
  `leaf` bit(1) DEFAULT NULL COMMENT '叶子节点(0:树枝节点;1:叶子节点)',
  `sortno` int(11) DEFAULT '0' COMMENT '排序号',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID',
  `menu_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名称',
  `iconcls` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '节点图标CSS类名',
  `expanded` bit(1) DEFAULT NULL COMMENT '展开状态(1:展开;0:收缩)',
  `url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '请求地址',
  `leaf` bit(1) DEFAULT NULL COMMENT '叶子节点(0:树枝节点;1:叶子节点)',
  `sortno` int(11) DEFAULT '0' COMMENT '排序号',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_menu_part`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_part`;
CREATE TABLE `sys_menu_part` (
  `part_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部件ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `cmp_id` varchar(50) COLLATE utf8_bin DEFAULT '0' COMMENT 'UI组件ID',
  `cmp_type` tinyint(4) DEFAULT NULL COMMENT 'UI组件类型(1:按钮组件;2:表单输入组件;3:容器面板组件)',
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单部件表，包括按钮，表单输入等信息。';

-- ----------------------------
-- Records of sys_menu_part
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `roletype` tinyint(4) DEFAULT '0' COMMENT '角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `locked` bit(1) DEFAULT b'0' COMMENT '锁定标志(1:锁定;0:激活)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`rolet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `authorize_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `authorize_level` tinyint(4) DEFAULT '0' COMMENT '权限级别(1:访问权限;2:管理权限)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`authorize_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_menu_part`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_part`;
CREATE TABLE `sys_role_menu_part` (
  `authorize_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `part_id` bigint(20) DEFAULT NULL COMMENT '组件ID',
  `part_auth_type` tinyint(4) DEFAULT NULL COMMENT 'UI组件权限类型(1:禁用;2:只读;3:编辑;4:显示;5:隐藏;6:挂起;7:激活;)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`authorize_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色菜单部件';

-- ----------------------------
-- Records of sys_role_menu_part
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `account` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '登陆帐户',
  `password` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `mobile` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别(0:未知;1:男;2:女)',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `locked` bit(1) DEFAULT b'0' COMMENT '锁定标志(1:锁定;0:激活)',
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `user_type` tinyint(4) DEFAULT NULL COMMENT '人员类型(1:业务人员;2:管理员;3:系统内置人员;)',
  `enabled` bit(1) DEFAULT b'0' COMMENT '启用状态(1:启用;0:不启用)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_menu_map`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu_map`;
CREATE TABLE `sys_user_menu_map` (
  `authorize_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `authorize_level` tinyint(4) DEFAULT '0' COMMENT '权限级别(1:访问权限;2:管理权限)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`authorize_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户菜单映射';

-- ----------------------------
-- Records of sys_user_menu_map
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_menu_part`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu_part`;
CREATE TABLE `sys_user_menu_part` (
  `authorize_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `part_id` bigint(20) DEFAULT NULL COMMENT '组件ID',
  `part_auth_type` tinyint(4) DEFAULT NULL COMMENT 'UI组件权限类型(1:禁用;2:只读;3:编辑;4:显示;5:隐藏;6:挂起;7:激活;)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`authorize_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户菜单部件';

-- ----------------------------
-- Records of sys_user_menu_part
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `authorize_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`authorize_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色权限';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
