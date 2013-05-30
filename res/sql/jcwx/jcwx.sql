CREATE DATABASE `db_weixin` /*!40100 DEFAULT CHARACTER SET utf8 */

CREATE TABLE `jcwx_category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `cname` varchar(255) DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `jcwx_article` (
  `article_id` int(11) NOT NULL DEFAULT '0' COMMENT '微信id',
  `title` varchar(255) DEFAULT NULL COMMENT '微信名称',
  `views` int(11) DEFAULT '0' COMMENT '关注人数',
  `likes` int(11) DEFAULT '0' COMMENT '关注他人数',
  `wxh` varchar(255) DEFAULT NULL COMMENT '微信号',
  `wxqr` varchar(255) DEFAULT NULL,
  `content` text COMMENT '微信介绍',
  `first_category_id` int(11) DEFAULT NULL COMMENT '一级类别id',
  `first_category_name` varchar(255) DEFAULT NULL COMMENT '一级类别名称',
  `second_category_name` varchar(255) DEFAULT NULL COMMENT '二级类别名称',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '标题图片路径',
  `created_at` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `article_id` (`article_id`),
  KEY `title` (`title`),
  KEY `first_category_name` (`first_category_name`),
  KEY `second_category_name` (`second_category_name`),
  KEY `first_category_id` (`first_category_id`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;