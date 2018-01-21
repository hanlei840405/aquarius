/*
Navicat MySQL Data Transfer

Source Server         : 192.168.99.100
Source Server Version : 50721
Source Host           : 192.168.99.100:32768
Source Database       : system

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-01-22 00:44:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `full_path` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `location_code` varchar(255) DEFAULT NULL,
  `department_code` varchar(255) DEFAULT NULL,
  `parent` tinyint(1) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES ('52', '00000068', '集团', '00000068', '集团', '00000001', null, '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('53', '00000069', 'CEO', '00000068-00000069', '集团-CEO', '00000001', '00000068', '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('54', '00000070', 'COO', '00000068-00000070', '集团-COO', '00000001', '00000068', '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('55', '00000071', 'CTO', '00000068-00000071', '集团-CTO', '00000001', '00000068', '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('56', '00000072', 'CFO', '00000068-00000072', '集团-CFO', '00000001', '00000068', '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('57', '00000073', 'CHO', '00000068-00000073', '集团-CHO', '00000001', '00000068', '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('58', '00000074', 'Lib', '00000068-00000071-00000074', '集团-CTO-Lib', '00000001', '00000071', '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('59', '00000075', 'Account', '00000068-00000072-00000075', '集团-CFO-Account', '00000001', '00000072', '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('60', '00000076', '主任1', '00000068-00000070-00000076', '集团-COO-主任1', '00000001', '00000070', '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('63', '00000079', 'v12', '00000068-00000079', '集团-v12', '00000001', '00000069', '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('64', '00000080', 'CMO', '00000068-00000080', '集团-CMO', '00000001', '00000068', '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('66', '00000084', 'v11', '00000068-00000069-00000084', '集团-CEO-v11', '00000001', '00000079', '0', '启用', null, null, null, '2018-01-17 00:37:31', '2');
INSERT INTO `sys_department` VALUES ('67', '00000085', 'aaa', '00000068-00000085', '集团-aaa', '00000001', '00000068', '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('68', '00000086', 'bbb', '00000086', 'bbb', '00000001', null, '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('69', '00000087', 'ccc', '00000087', 'ccc', '00000001', null, '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_department` VALUES ('70', '00000088', 'dddd', '00000068-00000069-00000084-00000088', '集团-CEO-v11-dddd', '00000001', '00000084', '0', '删除', null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_location
-- ----------------------------
DROP TABLE IF EXISTS `sys_location`;
CREATE TABLE `sys_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_location
-- ----------------------------
INSERT INTO `sys_location` VALUES ('1', '00000001', 'CCPARK', '启用', null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_part_time
-- ----------------------------
DROP TABLE IF EXISTS `sys_part_time`;
CREATE TABLE `sys_part_time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(255) DEFAULT NULL,
  `position_code` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_part_time
-- ----------------------------

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `department_code` varchar(255) DEFAULT NULL,
  `position_code` varchar(255) DEFAULT NULL,
  `manager` tinyint(1) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_position
-- ----------------------------
INSERT INTO `sys_position` VALUES ('1', '00000001', '机构负责人', '00000068', '00000001', '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_position` VALUES ('2', '00000002', '助理', '00000068', '00000001', '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_position` VALUES ('3', '00000003', '岗位负责人2', null, null, '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_position` VALUES ('4', '00000004', '岗位负责人1212', null, null, '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_position` VALUES ('5', '00000005', '岗位负责人F', '00000072', '00000005', '0', '启用', null, null, null, null, '0');
INSERT INTO `sys_position` VALUES ('6', '00000006', '岗位负责人', '00000073', null, '1', '启用', null, null, null, null, '0');
INSERT INTO `sys_position` VALUES ('7', '00000007', 'aaa1', null, null, '0', '删除', null, null, null, null, '0');
INSERT INTO `sys_position` VALUES ('8', '00000008', 'aaa', null, null, '0', '删除', null, null, null, null, '0');
INSERT INTO `sys_position` VALUES ('9', '00000015', '助理1', '00000068', '00000001', '0', '启用', null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `resource_code` varchar(255) DEFAULT NULL,
  `full_path` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '00000001', '啊啊啊', null, '00000001', '启用', null, null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('2', '00000002', '啊啊啊', '00000001', '00000001-00000002', '启用', null, null, null, null, '0');
INSERT INTO `sys_resource` VALUES ('3', '00000003', 'bbb', '00000001', '00000001-00000003', '启用', null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_resource_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource_position`;
CREATE TABLE `sys_resource_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_code` varchar(255) NOT NULL,
  `position_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource_position
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `entry_day` varchar(255) DEFAULT NULL,
  `regular_day` varchar(255) DEFAULT NULL,
  `leave_day` varchar(255) DEFAULT NULL,
  `department_code` varchar(255) DEFAULT NULL,
  `position_code` varchar(255) DEFAULT NULL,
  `head_img` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '00000001', '韩磊', null, '18660791231', '男', '2018-01-19T16:00:00.000Z', '2018-01-19T16:00:00.000Z', '2018-01-19T16:00:00.000Z', null, '00000068', '00000001', '00000001.png', '启用', null, null, null, null, '0');
INSERT INTO `sys_user` VALUES ('2', '00000002', '啊啊啊', '111@111.com', '111', '男', '2018-01-20T16:00:00.000Z', '2018-01-20T16:00:00.000Z', '2018-01-20T16:00:00.000Z', null, '00000072', '00000005', null, '启用', null, null, null, null, '0');
