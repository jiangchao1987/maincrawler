package com.candou.ac.rom.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.candou.ac.rom.bean.RomApp;
import com.candou.util.DateTimeUtil;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RomAppDao extends JdbcDaoSupport implements IRomAppDao {
	private static Logger log = Logger.getLogger(RomAppDao.class);

	private static final String ROMAPP_DESCRIPTION_UPDATE_BY_APPID = "update tb_app set description = ? where app_id = ?";
	private static final String ROMAPP_SELECT = "select app_id, url from tb_app";
	private static final String ROMAPP_SELECT_BY_FILENAME_NOTNULL = "select app_id, download_url, filename, size from tb_app where !isnull(filename)";
	private static final String ROMAPP_SELECT_BY_APPID = "select count(0) from tb_app where app_id = ?";
	private static final String ROMAPP_INSERT = "insert ignore into tb_app (app_id, app_name, author, fit_type, size, release_date, rom_type, star, description, icon_url, url, download_url, category_id, category_name, company, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String ROMAPP_SELECT_BY_FILENAME_NULL = "select app_id, download_url from tb_app where isnull(filename)";
	private static final String ROMAPP_UPDATE_BY_APPID = "update tb_app set filename = ?, updated_at = ? where app_id = ?";
	private static final String ROMAPP_UPDATE_BY_FILENAME = "update tb_app set filename = ? where filename like ?";

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateDescription(RomApp app) {
		getJdbcTemplate().update(ROMAPP_DESCRIPTION_UPDATE_BY_APPID, app.getDescription(), app.getAppId());
	}

	@Override
	public List<RomApp> findAllApps() {
		return getJdbcTemplate().query(ROMAPP_SELECT, ParameterizedBeanPropertyRowMapper.newInstance(RomApp.class));
	}

	@Override
	public List<RomApp> findAvailableApps() {
		return getJdbcTemplate().query(ROMAPP_SELECT_BY_FILENAME_NOTNULL,
				ParameterizedBeanPropertyRowMapper.newInstance(RomApp.class));
	}

	@Override
	public boolean exists(int appId) {
		int count = getJdbcTemplate().queryForInt(ROMAPP_SELECT_BY_APPID, appId);
		return count > 0 ? true : false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addBatchApps(List<RomApp> apps) {
		for (RomApp app : apps) {
			String now = DateTimeUtil.nowDateTime();
			log.info(app);
			getJdbcTemplate().update(ROMAPP_INSERT, app.getAppId(), app.getAppName(), app.getAuthor(),
					app.getFitType(), app.getSize(), app.getReleaseDate(), app.getRomType(), app.getStar(),
					app.getDescription(), app.getIconUrl(), app.getUrl(), app.getDownloadUrl(), app.getCategoryId(),
					app.getCategoryName(), app.getCompany(), now, now);
		}
	}

	@Override
	public List<RomApp> findApps() {
		return getJdbcTemplate().query(ROMAPP_SELECT_BY_FILENAME_NULL,
				ParameterizedBeanPropertyRowMapper.newInstance(RomApp.class));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateFileName(RomApp app) {
		String now = DateTimeUtil.nowDateTime();
		log.info(String.format("RomApp [appId=%s, filename=%s]", app.getAppId(), app.getFilename()));
		getJdbcTemplate().update(ROMAPP_UPDATE_BY_APPID, app.getFilename(), now, app.getAppId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateFileName(String filename) {
		log.info(String.format("update filename %s", filename));
		getJdbcTemplate().update(ROMAPP_UPDATE_BY_FILENAME, null, "%" + filename);
	}

}
