/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : sb_redis

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-11-04 09:10:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商品名称',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10012 DEFAULT CHARSET=utf8 COMMENT='商品信息表';

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('2', 'book10011', 'spring实战(第四版)', '2019-10-16 22:14:06');
INSERT INTO `item` VALUES ('4', 'book10013', 'spring boot实战', '2019-10-16 22:34:38');
INSERT INTO `item` VALUES ('5', 'book10014', 'effective java', '2019-10-16 23:04:26');
INSERT INTO `item` VALUES ('8', 'book40011', 'SpringBoot编程入门与实战2', null);
INSERT INTO `item` VALUES ('9', 'book40012', 'Java编程思想', null);
INSERT INTO `item` VALUES ('10', 'book40013', '深入分析Java Web技术内幕', null);
INSERT INTO `item` VALUES ('10011', 'book40014', '深入分析Java Web技术内幕2', null);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '通告标题',
  `content` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '内容',
  `is_active` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='通告';

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('11', '双11秒杀报名活动来啦！', '尊敬的商户您好，双11秒杀报名活动来啦。。。。', '1');
INSERT INTO `notice` VALUES ('12', '双12秒杀报名活动来啦！', '尊敬的商户您好，双12秒杀报名活动来啦。。。。', '1');
INSERT INTO `notice` VALUES ('13', '双13秒杀报名活动来啦！', '尊敬的商户您好，双13秒杀报名活动来啦。。。。', '1');
INSERT INTO `notice` VALUES ('14', '双14秒杀报名活动来啦！', '尊敬的商户您好，双14秒杀报名活动来啦。。。。', '1');
INSERT INTO `notice` VALUES ('15', '双15秒杀报名活动来啦！', '尊敬的商户您好，双15秒杀报名活动来啦。。。。', '1');
INSERT INTO `notice` VALUES ('16', '双17秒杀报名活动来啦！', '尊敬的商户您好，双17秒杀报名活动来啦。。。。', '1');
INSERT INTO `notice` VALUES ('17', '双18秒杀报名活动来啦！', '尊敬的商户您好，双18秒杀报名活动来啦。。。。', '1');

-- ----------------------------
-- Table structure for phone_fare
-- ----------------------------
DROP TABLE IF EXISTS `phone_fare`;
CREATE TABLE `phone_fare` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '手机号码',
  `fare` decimal(10,2) DEFAULT NULL COMMENT '充值金额',
  `is_active` tinyint(4) DEFAULT '1' COMMENT '是否有效(1=是;0=否)',
  PRIMARY KEY (`id`),
  KEY `idx_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='手机充值记录';

-- ----------------------------
-- Records of phone_fare
-- ----------------------------
INSERT INTO `phone_fare` VALUES ('36', '100', '100.00', '1');
INSERT INTO `phone_fare` VALUES ('37', '101', '110.00', '1');
INSERT INTO `phone_fare` VALUES ('38', '102', '85.00', '1');
INSERT INTO `phone_fare` VALUES ('39', '103', '99.00', '1');
INSERT INTO `phone_fare` VALUES ('40', '102', '15.00', '1');
INSERT INTO `phone_fare` VALUES ('41', '102', '100.00', '1');
INSERT INTO `phone_fare` VALUES ('42', '103', '100.00', '1');
INSERT INTO `phone_fare` VALUES ('43', '101', '200.00', '1');

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(150) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '问题标题',
  `choice_a` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '选项A',
  `choice_b` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '选项B',
  `is_active` tinyint(4) DEFAULT '1' COMMENT '是否有效(1=是;0=否)',
  `order_by` tinyint(4) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_title` (`title`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='问题库列表';

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES ('1', '这个APP好用吗？', '好用', '不好用', '1', '1');
INSERT INTO `problem` VALUES ('2', '你愿意推荐给你的好友使用吗？', '愿意', '不愿意', '1', '2');
INSERT INTO `problem` VALUES ('3', '这个APP的启动界面炫酷吗？', '炫酷', '丑', '1', '3');
INSERT INTO `problem` VALUES ('4', '我们的APP使用起来流畅吗？', '还行', '卡到爆', '1', '4');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '商品名称',
  `user_id` int(11) NOT NULL COMMENT '所属用户id',
  `scan_total` int(255) DEFAULT NULL COMMENT '浏览量',
  `is_active` tinyint(255) DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`id`),
  KEY `indx_scan_total` (`scan_total`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='商户商品表';

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('19', 'SpringBoot项目实战', '10010', '10', '1');
INSERT INTO `product` VALUES ('20', 'Redis入门与实战', '10010', '20', '1');
INSERT INTO `product` VALUES ('21', 'Rabbitmq入门与实战', '10010', '30', '1');
INSERT INTO `product` VALUES ('22', 'ZooKeeper入门与实战', '10011', '10', '1');
INSERT INTO `product` VALUES ('23', 'Kafka入门与实战', '10011', '20', '1');
INSERT INTO `product` VALUES ('24', 'a', '10011', '20', '1');
INSERT INTO `product` VALUES ('25', 'b', '10011', '20', '1');
INSERT INTO `product` VALUES ('26', '1', '10011', '20', '1');
INSERT INTO `product` VALUES ('27', '2', '10011', '20', '1');
INSERT INTO `product` VALUES ('28', '3', '10011', '20', '1');

-- ----------------------------
-- Table structure for red_detail
-- ----------------------------
DROP TABLE IF EXISTS `red_detail`;
CREATE TABLE `red_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `record_id` int(11) NOT NULL COMMENT '红包记录id',
  `amount` decimal(8,2) DEFAULT NULL COMMENT '每个小红包的金额（单位为分）',
  `is_active` tinyint(4) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8 COMMENT='红包明细金额';

-- ----------------------------
-- Records of red_detail
-- ----------------------------
INSERT INTO `red_detail` VALUES ('183', '22', '7.00', '1', null);
INSERT INTO `red_detail` VALUES ('184', '22', '13.00', '1', null);
INSERT INTO `red_detail` VALUES ('185', '22', '5.00', '1', null);
INSERT INTO `red_detail` VALUES ('186', '22', '2.00', '1', null);
INSERT INTO `red_detail` VALUES ('187', '22', '13.00', '1', null);
INSERT INTO `red_detail` VALUES ('188', '22', '10.00', '1', null);
INSERT INTO `red_detail` VALUES ('189', '22', '17.00', '1', null);
INSERT INTO `red_detail` VALUES ('190', '22', '6.00', '1', null);
INSERT INTO `red_detail` VALUES ('191', '22', '21.00', '1', null);
INSERT INTO `red_detail` VALUES ('192', '22', '6.00', '1', null);

-- ----------------------------
-- Table structure for red_record
-- ----------------------------
DROP TABLE IF EXISTS `red_record`;
CREATE TABLE `red_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `red_packet` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '红包全局唯一标识串',
  `total` int(11) NOT NULL COMMENT '人数',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '总金额（单位为分）',
  `is_active` tinyint(4) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='发红包记录';

-- ----------------------------
-- Records of red_record
-- ----------------------------
INSERT INTO `red_record` VALUES ('20', '10011', 'SpringBootRedis:RedPacket:10011:1190827725347368960', '10', '100.00', '1', '2019-11-03 11:07:09');
INSERT INTO `red_record` VALUES ('21', '10012', 'SpringBootRedis:RedPacket:10012:1190837219003478016', '10', '100.00', '1', '2019-11-03 11:44:52');
INSERT INTO `red_record` VALUES ('22', '10013', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '10', '100.00', '1', '2019-11-03 11:46:41');

-- ----------------------------
-- Table structure for red_rob_record
-- ----------------------------
DROP TABLE IF EXISTS `red_rob_record`;
CREATE TABLE `red_rob_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户账号',
  `red_packet` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '红包标识串',
  `amount` decimal(8,2) DEFAULT NULL COMMENT '红包金额（单位为分）',
  `rob_time` datetime DEFAULT NULL COMMENT '时间',
  `is_active` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 COMMENT='抢红包记录';

-- ----------------------------
-- Records of red_rob_record
-- ----------------------------
INSERT INTO `red_rob_record` VALUES ('168', '80012', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '6.00', '2019-11-03 11:47:08', '1');
INSERT INTO `red_rob_record` VALUES ('169', '80014', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '13.00', '2019-11-03 11:47:08', '1');
INSERT INTO `red_rob_record` VALUES ('170', '80010', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '6.00', '2019-11-03 11:47:08', '1');
INSERT INTO `red_rob_record` VALUES ('171', '80011', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '5.00', '2019-11-03 11:47:08', '1');
INSERT INTO `red_rob_record` VALUES ('172', '80013', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '21.00', '2019-11-03 11:47:08', '1');
INSERT INTO `red_rob_record` VALUES ('173', '90017', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '7.00', '2019-11-03 11:49:12', '1');
INSERT INTO `red_rob_record` VALUES ('174', '90013', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '2.00', '2019-11-03 11:49:12', '1');
INSERT INTO `red_rob_record` VALUES ('175', '90010', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '13.00', '2019-11-03 11:49:12', '1');
INSERT INTO `red_rob_record` VALUES ('176', '90014', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '10.00', '2019-11-03 11:49:12', '1');
INSERT INTO `red_rob_record` VALUES ('177', '90019', 'SpringBootRedis:RedPacket:10013:1190837674077073408', '17.00', '2019-11-03 11:49:12', '1');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '字典类型',
  `name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '字典名称',
  `code` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '选项编码',
  `value` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '选项取值',
  `order_by` int(11) DEFAULT '1' COMMENT '排序',
  `is_active` tinyint(4) DEFAULT '1' COMMENT '是否有效(1=是;0=否)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_type_code` (`type`,`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='字典配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'ReviewStatus', '审核状态', 'Passed', '通过', '1', '1', '2019-10-25 22:03:02');
INSERT INTO `sys_config` VALUES ('2', 'ReviewStatus', '审核状态', 'NotPassed', '不通过', '2', '1', '2019-10-25 22:03:02');
INSERT INTO `sys_config` VALUES ('3', 'Sex', '性别', 'Female', '女性', '1', '1', '2019-10-25 22:03:02');
INSERT INTO `sys_config` VALUES ('4', 'Sex', '性别', 'Male', '男性', '2', '1', '2019-10-25 22:03:02');
INSERT INTO `sys_config` VALUES ('5', 'Color', '颜色', 'Red', '红色', '1', '1', '2019-10-25 22:54:02');
INSERT INTO `sys_config` VALUES ('6', 'Color', '颜色', 'Black', '黑色', '2', '1', '2019-10-25 22:54:38');
INSERT INTO `sys_config` VALUES ('7', 'Color', '颜色', 'White', '白色', '3', '1', '2019-10-25 22:54:58');
INSERT INTO `sys_config` VALUES ('8', 'Color', '颜色', 'Pink', '粉色', '4', '1', '2019-10-25 22:55:09');
INSERT INTO `sys_config` VALUES ('9', 'Color', '颜色', 'brond', '棕色', '5', '1', '2019-10-31 21:34:13');
INSERT INTO `sys_config` VALUES ('10', 'HouseType', '户型', 'ThreeTwo', '三房两厅', '1', '1', '2019-10-31 21:42:28');
INSERT INTO `sys_config` VALUES ('11', 'HouseType', '户型', 'TwoOne', '两房一厅', '2', '1', '2019-10-31 21:43:05');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '张三', '1948831260@qq.com');
INSERT INTO `user` VALUES ('2', '李四', 'linsenzhong@126.com');
INSERT INTO `user` VALUES ('23', '王五', '1974544863@qq.com');
INSERT INTO `user` VALUES ('47', 'lixiaolong', 'lixiaolong@126.com');
INSERT INTO `user` VALUES ('50', 'bruce Lee', 'Lee@126.com');
INSERT INTO `user` VALUES ('51', ' Lee', '4863@qq.com');
