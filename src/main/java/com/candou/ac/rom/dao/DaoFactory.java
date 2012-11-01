package com.candou.ac.rom.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoFactory {

	private static ApplicationContext ctx;

	private DaoFactory() {
	}

	static {
		ctx = new ClassPathXmlApplicationContext("com/candou/ac/rom/androidrom-jdbc.xml");
	}

	public static IRomAppDao getRomAppDao() {
		return (IRomAppDao) ctx.getBean("romAppDao");
	}

	public static RomPhotoDao getRomPhotoDao() {
		return (RomPhotoDao) ctx.getBean("romPhotoDao");
	}

}
