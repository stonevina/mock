/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50614
Source Host           : localhost:3306
Source Database       : mock

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2015-02-13 11:17:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mock
-- ----------------------------
DROP TABLE IF EXISTS `mock`;
CREATE TABLE `mock` (
  `mockID` int(11) NOT NULL AUTO_INCREMENT,
  `mockName` varchar(100) NOT NULL,
  `url` varchar(100) NOT NULL,
  `content` text,
  `createTime` varchar(100) DEFAULT NULL,
  `modifyTime` varchar(100) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `mockGroup` varchar(10) DEFAULT NULL,
  `mockStatus` int(11) DEFAULT '1',
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`mockID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
