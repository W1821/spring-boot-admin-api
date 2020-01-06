/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : base_admin_api_base

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-01-06 14:43:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for button
-- ----------------------------
DROP TABLE IF EXISTS `button`;
CREATE TABLE `button` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `actions` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `route_path` varchar(255) DEFAULT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf63llkiy6dekuxkbbxxyawmxw` (`menu_id`),
  CONSTRAINT `button_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of button
-- ----------------------------
INSERT INTO `button` VALUES ('1', '/user/check/number/{phoneNumber},/user/add,/role/main/list', 'add', '增加', '', '3');
INSERT INTO `button` VALUES ('2', '/user/query/{id},/user/update,/user/check/number/{phoneNumber},/role/main/list', 'edit', '编辑', '', '3');
INSERT INTO `button` VALUES ('3', '/user/delete/{id}', 'delete', '删除', '', '3');
INSERT INTO `button` VALUES ('4', '/user/list', 'search', '查询', '', '3');
INSERT INTO `button` VALUES ('5', '/role/query/{id},/role/edit', 'edit', '编辑', '', '4');
INSERT INTO `button` VALUES ('6', '/role/add', 'add', '增加', '', '4');
INSERT INTO `button` VALUES ('7', '/role/delete/{id}', 'delete', '删除', '', '4');
INSERT INTO `button` VALUES ('8', '/role/list', 'search', '查询', '', '4');
INSERT INTO `button` VALUES ('9', '/menu/list', 'search', '查询', '', '5');
INSERT INTO `button` VALUES ('10', '/menu/query/{id},/menu/edit', 'edit', '编辑', '', '5');
INSERT INTO `button` VALUES ('11', '/menu/delete', 'delete', '删除', '', '5');
INSERT INTO `button` VALUES ('12', '/menu/add', 'addSub', '添加下级', '', '5');
INSERT INTO `button` VALUES ('13', '/menu/add', 'add', '添加', '', '5');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `actions` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `menu_name` varchar(255) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `pids` varchar(255) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `route_path` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '/file/upload/image,/menu/main/list', '2018-05-17 16:00:28.000000', '0', 'setting', '系统', null, '', '1', '', '2019-12-17 08:18:33.000000');
INSERT INTO `menu` VALUES ('2', '', '2017-09-29 15:36:29.000000', '0', 'setting', '系统设置', '1', '1', '2', '', '2018-09-27 17:30:29.000000');
INSERT INTO `menu` VALUES ('3', '', '2017-09-29 16:38:30.000000', '0', '', '人员管理', '2', '1,2', '1', '/main/system/user/list', '2019-12-26 02:28:16.000000');
INSERT INTO `menu` VALUES ('4', '', '2017-09-29 16:58:50.000000', '0', '', '角色管理', '2', '1,2', '2', '/main/system/role/list', '2019-11-11 03:45:12.000000');
INSERT INTO `menu` VALUES ('5', '', '2017-09-29 15:36:57.000000', '0', '', '菜单管理', '2', '1,2', '3', '/main/system/menu/list', '2019-11-11 03:46:55.000000');
INSERT INTO `menu` VALUES ('6', '', '2019-12-27 17:45:39.000000', '0', 'user', '我的', '1', '1', '1', '', '2019-12-17 08:17:52.000000');
INSERT INTO `menu` VALUES ('7', '', '2018-09-27 18:55:05.000000', '0', '', '欢迎页', '6', '1,6', '1', '/main/welcome', '2018-09-28 09:45:46.000000');
INSERT INTO `menu` VALUES ('8', '', '2019-11-11 09:21:04.000000', '0', 'apple', 'Demo', null, '', '2', '', '2019-12-25 09:07:37.000000');
INSERT INTO `menu` VALUES ('9', '', '2019-11-11 09:21:27.000000', '0', 'radar-chart', '经典', '8', '8', '1', '', '2019-12-04 09:36:12.000000');
INSERT INTO `menu` VALUES ('10', '', '2019-11-11 09:21:36.000000', '0', '', '地域管理', '9', '8,9', '1', '', '2019-12-25 07:06:27.000000');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `role_status` int(11) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '2019-12-27 10:00:49.832000', '0', '管理员', '管理员', '0', '2020-01-03 03:39:58.521000');

-- ----------------------------
-- Table structure for role_button
-- ----------------------------
DROP TABLE IF EXISTS `role_button`;
CREATE TABLE `role_button` (
  `role_id` bigint(20) NOT NULL,
  `button_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`button_id`),
  KEY `FK6qydmhre4n7es8j89q4iorqvo` (`button_id`),
  CONSTRAINT `role_button_ibfk_1` FOREIGN KEY (`button_id`) REFERENCES `button` (`id`),
  CONSTRAINT `role_button_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_button
-- ----------------------------
INSERT INTO `role_button` VALUES ('1', '1');
INSERT INTO `role_button` VALUES ('1', '2');
INSERT INTO `role_button` VALUES ('1', '3');
INSERT INTO `role_button` VALUES ('1', '4');
INSERT INTO `role_button` VALUES ('1', '5');
INSERT INTO `role_button` VALUES ('1', '6');
INSERT INTO `role_button` VALUES ('1', '7');
INSERT INTO `role_button` VALUES ('1', '8');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `FKfg4e2mb2318tph615gh44ll3` (`menu_id`),
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '3');
INSERT INTO `role_menu` VALUES ('1', '4');
INSERT INTO `role_menu` VALUES ('1', '7');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_status` int(11) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `head_picture_url` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `super_admin` int(11) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '0', '0', '/public/image/2020/1/3/8488e75b-a209-48f5-b0a5-0c4367d6c6d4.png', '{bcrypt}$2a$10$vCxtGbkZ5FZOox0w4Y0aSeiR8C4PVM4I727HAQw5vooYyGEn4bsaa', '15256639988', '1', '2020-01-03 03:39:50.399000', '超级大boss', '2020-01-03 11:15:25.000000');
INSERT INTO `user` VALUES ('2', '0', '0', null, '{bcrypt}$2a$10$s5gBtxOGWi8K9618XrpRceeXqvx/hoECs57aXyCS7lwd1GX0TQbHO', '18888888888', '0', '2019-12-27 10:02:39.251000', '管理员A', '2020-01-03 11:15:29.000000');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '1');
