package com.candou.ac.rom.dao;

import java.util.List;

import com.candou.ac.rom.bean.RomApp;

public interface IRomAppDao {

	void updateDescription(RomApp app);

	List<RomApp> findAllApps();

	List<RomApp> findAvailableApps();

	boolean exists(int appId);

	void addBatchApps(List<RomApp> apps);

	List<RomApp> findApps();

	void updateFileName(RomApp app);

	void updateFileName(String filename);

}
