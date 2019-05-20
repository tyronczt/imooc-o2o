/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.12 : Database - o2o
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`o2o` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `o2o`;

/*Table structure for table `tb_area` */

DROP TABLE IF EXISTS `tb_area`;

CREATE TABLE `tb_area` (
  `area_id` int(2) NOT NULL AUTO_INCREMENT COMMENT '区域ID',
  `area_name` varchar(200) NOT NULL COMMENT '名称',
  `priority` int(2) NOT NULL DEFAULT '0' COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='区域信息';

/*Table structure for table `tb_head_line` */

DROP TABLE IF EXISTS `tb_head_line`;

CREATE TABLE `tb_head_line` (
  `line_id` int(100) NOT NULL AUTO_INCREMENT COMMENT '头条ID',
  `line_name` varchar(1000) DEFAULT NULL COMMENT '名称',
  `line_link` varchar(2000) NOT NULL COMMENT '链接',
  `line_img` varchar(128) NOT NULL COMMENT '图片',
  `priority` int(2) NOT NULL DEFAULT '0' COMMENT '权重',
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0:不可用;1:可用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最新修改时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='头条';

/*Table structure for table `tb_local_auth` */

DROP TABLE IF EXISTS `tb_local_auth`;

CREATE TABLE `tb_local_auth` (
  `local_auth_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) NOT NULL COMMENT '用户ID',
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最新修改时间',
  PRIMARY KEY (`local_auth_id`),
  UNIQUE KEY `uk_local_profile` (`username`),
  KEY `fk_localauth_profile` (`user_id`),
  CONSTRAINT `fk_localauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='本地用户信息';

/*Table structure for table `tb_person_info` */

DROP TABLE IF EXISTS `tb_person_info`;

CREATE TABLE `tb_person_info` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `profile_img` varchar(1024) DEFAULT NULL COMMENT '头像',
  `email` varchar(1024) DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0:禁止使用本商城;1:允许使用本商城',
  `user_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:顾客;2:店家;3:超级管理员',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` timestamp NULL DEFAULT NULL COMMENT '最新修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户信息';

/*Table structure for table `tb_product` */

DROP TABLE IF EXISTS `tb_product`;

CREATE TABLE `tb_product` (
  `product_id` int(100) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_category_id` int(11) DEFAULT NULL COMMENT '商品类别ID',
  `shop_id` int(20) NOT NULL DEFAULT '0' COMMENT '店铺ID',
  `product_name` varchar(256) NOT NULL COMMENT '名称',
  `product_desc` varchar(2000) DEFAULT NULL COMMENT '描述',
  `img_addr` varchar(2000) DEFAULT NULL COMMENT '缩略图',
  `normal_price` varchar(100) DEFAULT NULL COMMENT '原价',
  `promotion_price` varchar(100) DEFAULT NULL COMMENT '现价',
  `priority` int(2) NOT NULL DEFAULT '0' COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0.下架 1.可用',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最新修改时间',
  PRIMARY KEY (`product_id`),
  KEY `fk_product_shop_category` (`product_category_id`),
  KEY `fk_product_shop` (`shop_id`),
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`),
  CONSTRAINT `fk_product_shop_category` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='商品信息';

/*Table structure for table `tb_product_category` */

DROP TABLE IF EXISTS `tb_product_category`;

CREATE TABLE `tb_product_category` (
  `product_category_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `product_category_name` varchar(128) NOT NULL COMMENT '名称',
  `shop_id` int(20) NOT NULL DEFAULT '0' COMMENT '店铺ID',
  `priority` int(2) NOT NULL DEFAULT '0' COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`product_category_id`),
  KEY `fk_product_category_shop` (`shop_id`),
  CONSTRAINT `fk_product_category_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='商品类别';

/*Table structure for table `tb_product_img` */

DROP TABLE IF EXISTS `tb_product_img`;

CREATE TABLE `tb_product_img` (
  `product_img_id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `img_addr` varchar(2000) NOT NULL COMMENT '名称',
  `img_desc` varchar(1024) DEFAULT NULL COMMENT '描述',
  `product_id` int(20) DEFAULT NULL COMMENT '商品ID',
  `priority` int(2) DEFAULT '0' COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`product_img_id`),
  KEY `fk_product_img` (`product_id`),
  CONSTRAINT `fk_product_img` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='商品图片';

/*Table structure for table `tb_shop` */

DROP TABLE IF EXISTS `tb_shop`;

CREATE TABLE `tb_shop` (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '店铺ID',
  `area_id` int(5) DEFAULT NULL COMMENT '区域ID',
  `shop_category_id` int(11) DEFAULT NULL COMMENT '类别ID',
  `owner_id` int(10) DEFAULT NULL COMMENT '店铺创建人',
  `shop_name` varchar(256) NOT NULL COMMENT '名称',
  `shop_desc` varchar(1000) DEFAULT NULL COMMENT '描述',
  `shop_addr` varchar(1000) DEFAULT NULL COMMENT '位置',
  `phone` varchar(1000) DEFAULT NULL COMMENT '手机',
  `shop_img` varchar(2000) DEFAULT NULL COMMENT '图片',
  `priority` int(2) DEFAULT '0' COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0.不可用 1.可用 2.审核中',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最新修改时间',
  `advice` varchar(500) DEFAULT '' COMMENT '建议',
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_category` (`shop_category_id`),
  KEY `fk_shop_profile` (`owner_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `fk_shop_category` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`),
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='店铺信息';

/*Table structure for table `tb_shop_category` */

DROP TABLE IF EXISTS `tb_shop_category`;

CREATE TABLE `tb_shop_category` (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺ID',
  `shop_category_name` varchar(100) DEFAULT '' COMMENT '名称',
  `shop_category_desc` varchar(1000) NOT NULL COMMENT '描述',
  `shop_category_img` varchar(2000) NOT NULL COMMENT '图片',
  `priority` int(2) NOT NULL DEFAULT '0' COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最新修改时间',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级ID',
  PRIMARY KEY (`shop_category_id`),
  UNIQUE KEY `uk_shopcategory_profile` (`shop_category_name`),
  KEY `fk_shop_category_profile` (`parent_id`),
  CONSTRAINT `fk_shop_category_profile` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='店铺类别';

/*Table structure for table `tb_wechat_auth` */

DROP TABLE IF EXISTS `tb_wechat_auth`;

CREATE TABLE `tb_wechat_auth` (
  `wechat_auth_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) NOT NULL COMMENT '用户ID',
  `open_id` varchar(1024) NOT NULL COMMENT '微信ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`wechat_auth_id`),
  UNIQUE KEY `open_id` (`open_id`),
  KEY `fk_wechatauth_profile` (`user_id`),
  CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信用户信息';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
