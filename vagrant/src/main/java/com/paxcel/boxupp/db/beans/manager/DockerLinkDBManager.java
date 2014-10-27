package com.paxcel.boxupp.db.beans.manager;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import com.boxupp.db.beans.DockerLinkBean;
import com.boxupp.db.beans.MachineConfigurationBean;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.paxcel.boxupp.db.DAOProvider;
import com.paxcel.responseBeans.StatusBean;

public class DockerLinkDBManager {
	private static Logger logger = LogManager.getLogger(DockerLinkDBManager.class.getName());
	protected  static Dao<DockerLinkBean, Integer> dockerLinkDao = null;
	private static DockerLinkDBManager dockerLinkDBManager = null;

	public static DockerLinkDBManager getInstance(){
		if(dockerLinkDBManager == null){
			dockerLinkDBManager = new DockerLinkDBManager();
		}
		return dockerLinkDBManager;
		
	}
	
	private  DockerLinkDBManager() {
		dockerLinkDao = DAOProvider.getInstance().fetchDao(DockerLinkBean.class);
	}
	
	public StatusBean save(MachineConfigurationBean machineConfig, JsonNode dockerLinkMapping) {
		StatusBean statusBean = new StatusBean();
		//DockerLinkBean dockerLink = new DockerLinkBean();
		Gson portforwarded = new Gson();
		List<DockerLinkBean> dockerLinks   = (List<DockerLinkBean>) portforwarded.fromJson(dockerLinkMapping.toString(), DockerLinkBean.class);
		
		try {
			for(DockerLinkBean dockerLink : dockerLinks){
				dockerLink.setMachineConfig(machineConfig);
				dockerLinkDao.create(dockerLink);
			}
			
		} catch (SQLException e) {
			logger.error("Error creating a new sync folder mapping : " + e.getMessage());
			statusBean.setStatusCode(1);
			statusBean.setStatusMessage("Error in creating sync folder mapping :"+ e.getMessage());

		}
		statusBean.setStatusCode(0);
		statusBean.setStatusMessage("sync folder mapping create successfully");
		return statusBean;
	}
	public StatusBean update(ForeignCollection<DockerLinkBean> dockerLinks) {
		StatusBean statusBean = new StatusBean();
		
			try {
				for(DockerLinkBean dockerLink : dockerLinks){
					dockerLinkDao.update(dockerLink);
				}
			} catch (SQLException e) {
				statusBean.setStatusCode(1);
				statusBean.setStatusMessage("Error in dockerLink mapping update : "+e.getMessage());
			}
		
		statusBean.setStatusCode(0);
		statusBean.setStatusMessage("Docker link  update successfully");
		return statusBean;
	}
}
