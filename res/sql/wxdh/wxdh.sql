CREATE TABLE `wxdh_app` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `intro` varchar(200) DEFAULT NULL COMMENT '简介',
  `url` varchar(100) DEFAULT NULL COMMENT '微信关注跳转地址',
  `f` int(11) DEFAULT NULL,
  `oc` varchar(20) DEFAULT NULL COMMENT '信微号',
  `wsu` varchar(100) DEFAULT NULL,
  `detail` varchar(20) DEFAULT NULL,
  `dts` int(11) DEFAULT NULL COMMENT '能可是加入时间',
  `cid` int(11) DEFAULT NULL,
  `cname` varchar(20) DEFAULT NULL COMMENT '分类名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '头像',
  `icon_name` varchar(100) DEFAULT NULL,
  `imc` varchar(100) DEFAULT NULL COMMENT '二维码地址',
  `imc_name` varchar(100) DEFAULT NULL,
  `sc` int(11) DEFAULT NULL,
  `direct_number` int(11) DEFAULT NULL COMMENT '关注人数',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`),
  KEY `cid` (`cid`),
  KEY `cname` (`cname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `wxdh_category` (
  `cid` int(11) NOT NULL DEFAULT '0' COMMENT '分类ID',
  `cname` varchar(20) DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `wxdh_job` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `intro` varchar(200) DEFAULT NULL COMMENT '简介',
  `url` varchar(100) DEFAULT NULL COMMENT '微信关注跳转地址',
  `f` int(11) DEFAULT NULL,
  `oc` varchar(20) DEFAULT NULL COMMENT '信微号',
  `cid` int(11) DEFAULT NULL,
  `cname` varchar(20) DEFAULT NULL COMMENT '分类名称',
  `direct_number` int(11) DEFAULT NULL COMMENT '关注人数',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_matched` int(11) DEFAULT '0' COMMENT '是否已经抓取',
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `cid` (`cid`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `wxdh_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` int(11) DEFAULT NULL,
  `photo_url` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `appid_photourl` (`appid`,`photo_url`),
  KEY `app_id` (`appid`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

