main spider servers, contains android/iphone/ipad crawlers(爬虫)


以下按照packagename区分
ac(android crawler)
	rom -- androidrom crawler, 来源http://www.shendu.com/
ic(iphone/ipad crawler)
	market -- 获取各个市场的iphone/ipad应用数据
		official -- 官方排行榜，请参照
		apple search api(http://www.apple.com/itunes/affiliates/resources/documentation/itunes-store-web-service-search-api.html)
		和apple rss generator(http://itunes.apple.com/rss?cc=CN)
		pp -- 抓取pp助手win客户端中iphone/ipad应用数据
	rank   -- 排行榜
		repair -- 修复排行榜，曾经使用，现在废弃，以后遇到可参考，详见代码
	top    -- 热榜
		appstatics -- 分析祝老师推荐的一个iphone应用的测试爬虫

备注: 所有项目通用package
conf -- 配置
db -- 数据库连接实体
util -- 工具类

log4j.properties -- log4j配置文件
