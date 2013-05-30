CREATE DATABASE `db_weixin` /*!40100 DEFAULT CHARACTER SET utf8 */

CREATE TABLE `jcwx_category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `cname` varchar(255) DEFAULT NULL COMMENT '��������',
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `jcwx_article` (
  `article_id` int(11) NOT NULL DEFAULT '0' COMMENT '΢��id',
  `title` varchar(255) DEFAULT NULL COMMENT '΢������',
  `views` int(11) DEFAULT '0' COMMENT '��ע����',
  `likes` int(11) DEFAULT '0' COMMENT '��ע������',
  `wxh` varchar(255) DEFAULT NULL COMMENT '΢�ź�',
  `wxqr` varchar(255) DEFAULT NULL,
  `content` text COMMENT '΢�Ž���',
  `first_category_id` int(11) DEFAULT NULL COMMENT 'һ�����id',
  `first_category_name` varchar(255) DEFAULT NULL COMMENT 'һ���������',
  `second_category_name` varchar(255) DEFAULT NULL COMMENT '�����������',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '����ͼƬ·��',
  `created_at` varchar(255) DEFAULT NULL COMMENT '����ʱ��',
  `updated_at` varchar(255) DEFAULT NULL COMMENT '�޸�ʱ��',
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