package com.boxupp.db;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boxupp.db.beans.DefaultConfigurationBean;
import com.boxupp.db.beans.GitRepoBean;
import com.boxupp.db.beans.MachineConfigurationBean;
import com.boxupp.db.beans.MachineProjectMapping;
import com.boxupp.db.beans.ProjectBean;
import com.boxupp.db.beans.ProjectProviderMappingBean;
import com.boxupp.db.beans.ProviderBean;
import com.boxupp.db.beans.PuppetModuleBean;
import com.boxupp.db.beans.ShellScriptBean;
import com.boxupp.db.beans.UserDetailBean;
import com.boxupp.db.beans.UserProjectMapping;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBConnectionManager {
	private static Logger logger = LogManager.getLogger(DBConnectionManager.class.getName());
	
	private static DBConnectionManager dbManager;
	private static ConnectionSource connectionSource;
	
	public static DBConnectionManager getInstance(){
		if(dbManager == null){
			dbManager = new DBConnectionManager();
		}
		return dbManager;		
	}
	
	public DBConnectionManager(){
		initDBConnection();	
		initDB();		
	}
	
	private void initDBConnection(){
		try {
			connectionSource = new JdbcConnectionSource(DerbyConfig.JDBC_URL);
		} catch (SQLException e) {
			logger.fatal("Error fetching connection from " + DerbyConfig.JDBC_URL + " : "+e.getMessage());
		}
	}
	
	public ConnectionSource fetchDBConnection(){
		return connectionSource;
	}
	
	private boolean initDB(){
		return createDatabases();
	}
	
	private static boolean createDatabases(){
		try {
			
			//************* DELETE TABLES **************//
			TableUtils.dropTable(connectionSource, ProjectBean.class, true);
			TableUtils.dropTable(connectionSource, ProviderBean.class, true);
			TableUtils.dropTable(connectionSource, ProjectProviderMappingBean.class, true);
			TableUtils.dropTable(connectionSource, UserProjectMapping.class, true);
			TableUtils.dropTable(connectionSource, UserDetailBean.class, true);
			
			TableUtils.dropTable(connectionSource, ShellScriptBean.class, true);
			
			TableUtils.dropTable(connectionSource, PuppetModuleBean.class, true);
			
			TableUtils.dropTable(connectionSource, MachineConfigurationBean.class, true);
			TableUtils.dropTable(connectionSource, MachineProjectMapping.class, true);
			
			TableUtils.dropTable(connectionSource, DefaultConfigurationBean.class, true);
			TableUtils.dropTable(connectionSource, GitRepoBean.class, true);

			
			//************* CREATE TABLES **************//
			TableUtils.createTable(connectionSource, ProjectBean.class);
			TableUtils.createTable(connectionSource, ProviderBean.class);
			TableUtils.createTable(connectionSource, ProjectProviderMappingBean.class);
			TableUtils.createTable(connectionSource, UserProjectMapping.class);
			TableUtils.createTable(connectionSource, UserDetailBean.class);
			
			TableUtils.createTable(connectionSource, ShellScriptBean.class);
			
			TableUtils.createTable(connectionSource, PuppetModuleBean.class);
			
			TableUtils.createTable(connectionSource, MachineConfigurationBean.class);
			TableUtils.createTable(connectionSource, MachineProjectMapping.class);
			
			

			TableUtils.createTable(connectionSource, DefaultConfigurationBean.class);
			TableUtils.createTable(connectionSource, GitRepoBean.class);
			
			//***************CREATE_ENTRIES**********************//
			
			ProviderBean provider1 = new ProviderBean();
			provider1.setDisabled(false);
			provider1.setName("VirtualBox");
			
			ProviderBean provider2 = new ProviderBean();
			provider2.setDisabled(false);
			provider2.setName("Docker");
			
			DAOProvider.getInstance().fetchProviderDao().create(provider1);
			
			DAOProvider.getInstance().fetchProviderDao().create(provider2);
			
			//**************CREATE_ENTRIES***********************//
			
			System.out.println("Created tables for mapping");
		} catch (SQLException e) {
			System.out.println("Error creating table : " + e.getMessage());
			e.printStackTrace();
			logger.error("Error creating Tables on the database : "+e.getMessage());
			return false;
		}
		return true;
	}
		
	public boolean releaseConnection(ConnectionSource connection){
		try {
			connection.close();
		} catch (SQLException e) {
			logger.debug("Error releasing the connection");
			return false;
		}
		return true;
	}

}
