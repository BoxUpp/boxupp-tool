package com.boxupp.db.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "syncFolder")
public class SyncFoldersBean {
	public static final String MACHINE_ID_FIELD_NAME = "machine_id";
	
	@DatabaseField(canBeNull = false, generatedId= true, useGetSet = true)
	private Integer syncFolderId;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = MACHINE_ID_FIELD_NAME)
	MachineConfigurationBean machineConfig;
	
	@DatabaseField(useGetSet = true)
	private String hostFolder;
	
	@DatabaseField(useGetSet = true)
	private String vmFolder;
	
	public Integer getSyncFolderId() {
		return syncFolderId;
	}
	public void setSyncFolderId(Integer syncFolderId) {
		this.syncFolderId = syncFolderId;
	}
	public String getHostFolder() {
		return hostFolder;
	}
	public void setHostFolder(String hostFolder) {
		this.hostFolder = hostFolder;
	}
	public String getVmFolder() {
		return vmFolder;
	}
	public void setVmFolder(String vmFolder) {
		this.vmFolder = vmFolder;
	}
	public MachineConfigurationBean getMachineConfig() {
		return machineConfig;
	}
	public void setMachineConfig(MachineConfigurationBean machineConfig) {
		this.machineConfig = machineConfig;
	}
	
}
