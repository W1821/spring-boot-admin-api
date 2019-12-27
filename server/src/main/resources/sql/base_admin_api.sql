/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : base_admin_api

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-10-24 16:17:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for button
-- ----------------------------
DROP TABLE IF EXISTS `button`;
CREATE TABLE `button` (
  `id` bigint(20) NOT NULL,
  `actions` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `route_path` varchar(255) DEFAULT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf63llkiy6dekuxkbbxxyawmxw` (`menu_id`),
  CONSTRAINT `FKf63llkiy6dekuxkbbxxyawmxw` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of button
-- ----------------------------
INSERT INTO `button` VALUES ('0', '/menu/save,/menu/query/{id}', 'add', '添加', '/main/system/menu/edit/:type/:id', '3');
INSERT INTO `button` VALUES ('28', null, 'b', 'b', null, '5');
INSERT INTO `button` VALUES ('29', null, 'c', 'c', null, '5');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('99');
INSERT INTO `hibernate_sequence` VALUES ('99');
INSERT INTO `hibernate_sequence` VALUES ('99');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL,
  `actions` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `menu_name` varchar(255) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `pids` varchar(255) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `router_urls` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `route_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '/file/upload/image,/file/ck/upload/image', '2018-05-17 16:00:28', '0', 'setting', '系统', null, '', '1', '', null, '');
INSERT INTO `menu` VALUES ('2', '', '2017-09-29 15:36:29', '0', 'setting', '系统设置', '1', null, '2', '', '2018-09-27 17:30:29', '');
INSERT INTO `menu` VALUES ('3', '/menu/list,/menu/save,/menu/query/{id},/menu/delete', '2017-09-29 15:36:57', '0', '', '菜单管理', '2', '1,2', '3', '/main/system/menu/edit/:type/:id', null, '/main/system/menu/list');
INSERT INTO `menu` VALUES ('5', '/user/list,/user/list,/user/query/{id},/user/check/number/{phoneNumber},/user/save,/user/delete/{id},/role/main/list', '2017-09-29 16:38:30', '0', '', '人员管理', '2', '1,2', '1', '/main/system/user/edit/:id', '2018-11-28 13:42:40', '/main/system/user/list');
INSERT INTO `menu` VALUES ('6', '/role/list,/role/query/{id},/role/save,/role/delete/{id}', '2017-09-29 16:58:50', '0', '', '角色管理', '2', '1,2', '2', '/main/system/role/edit/:id', null, '/main/system/role/list');
INSERT INTO `menu` VALUES ('13', '', '2018-09-27 17:28:03', '0', 'user', '我的', '1', '1', '1', '', '2018-09-28 17:40:15', '');
INSERT INTO `menu` VALUES ('15', '', '2018-09-27 18:55:05', '0', '', '欢迎页', '13', '1,13', null, '', '2018-09-28 09:45:46', '/main/welcome');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `role_status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '2018-06-01 16:30:01', '0', '管理员', '管理员', '0', '2018-09-28 17:39:32');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  KEY `FKfg4e2mb2318tph615gh44ll3` (`menu_id`) USING BTREE,
  KEY `FKqyvxw2gg2qk0wld3bqfcb58vq` (`role_id`) USING BTREE,
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '3');
INSERT INTO `role_menu` VALUES ('1', '5');
INSERT INTO `role_menu` VALUES ('1', '6');
INSERT INTO `role_menu` VALUES ('1', '15');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `account_status` int(11) DEFAULT NULL,
  `account_type` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `area_id` int(11) DEFAULT NULL,
  `area_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `head_picture_url` varchar(255) DEFAULT NULL,
  `id_card` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `super_admin` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('0', '0', '0', null, null, null, '2018-05-17 15:06:49', '0', '/public/image/b2f4cab27ffca3c7a6eb7c92cf824236.jpg', null, '{bcrypt}$2a$10$vIffpVpQVJWhGp.d5J.qLePRaddTJi/yvXj715TLUbCjaY3rxFofO', '15256639988', '1', '2018-09-29 10:11:44', '超级大boss');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`) USING BTREE,
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('0', '1');
