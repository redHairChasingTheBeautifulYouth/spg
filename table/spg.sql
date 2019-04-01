/*
 Navicat Premium Data Transfer

 Source Server         : 阿炳的
 Source Server Type    : MySQL
 Source Server Version : 50087
 Source Host           : localhost:3306
 Source Schema         : spg

 Target Server Type    : MySQL
 Target Server Version : 50087
 File Encoding         : 65001

 Date: 01/04/2019 21:10:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `domain_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, 'localhost');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL,
  `app_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `picture_url` varchar(150) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `room_id` int(11) UNSIGNED NOT NULL,
  `time` bigint(20) UNSIGNED NOT NULL,
  `message_type` tinyint(4) UNSIGNED NOT NULL,
  `message` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `room_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES (1, '小私群');
INSERT INTO `room` VALUES (2, '小私群');
INSERT INTO `room` VALUES (3, '小私群');
INSERT INTO `room` VALUES (4, '小私群');
INSERT INTO `room` VALUES (5, '小私群');
INSERT INTO `room` VALUES (6, '小私群');
INSERT INTO `room` VALUES (7, '小私群');
INSERT INTO `room` VALUES (8, '小私群');
INSERT INTO `room` VALUES (9, '小私群');

-- ----------------------------
-- Table structure for super_admin
-- ----------------------------
DROP TABLE IF EXISTS `super_admin`;
CREATE TABLE `super_admin`  (
  `id` int(11) UNSIGNED NOT NULL,
  `account` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `error_num` int(11) UNSIGNED NOT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of super_admin
-- ----------------------------
INSERT INTO `super_admin` VALUES (1, '123456', '123456', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `hash` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_picture_url` varchar(150) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'liubingxfqrewqrtq', 'liubin', '超级管理员', 'fsdagg');

-- ----------------------------
-- Table structure for user_room
-- ----------------------------
DROP TABLE IF EXISTS `user_room`;
CREATE TABLE `user_room`  (
  `id` int(11) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `room_id` int(10) UNSIGNED NOT NULL,
  `role_type` tinyint(4) UNSIGNED NOT NULL COMMENT '1为房主，2为管理员，3为普通成员，4为申请未通过的',
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
