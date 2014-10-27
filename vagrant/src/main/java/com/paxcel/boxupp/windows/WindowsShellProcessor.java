package com.paxcel.boxupp.windows;

import java.io.IOException;

import com.paxcel.boxupp.VagrantOutputStream;
import com.paxcel.boxupp.ws.OutputConsole;
import com.paxcel.responseBeans.VagrantStatus;

public class WindowsShellProcessor {
	private static WindowsShellExecutor shellExec = new WindowsShellExecutor();
	private static WindowsShellParser shellParser = new WindowsShellParser();
	
	public VagrantStatus checkVagrantStatus(String location){
		shellExec.setCMDExecDir(location);
		StringBuffer cmdOutput;
		cmdOutput = shellExec.checkVagrantStatusCMD("vagrant","status");
		return shellParser.parseVagrantStatusCMD(cmdOutput);
	}
	
	public String executeVagrantFile(String location, String command, OutputConsole consoleType) throws IOException, InterruptedException{
		
//		VagrantOutputStream.flushData();
		command = filterCommand(command);
		shellExec.setCMDExecDir(location);
		shellExec.bootVagrantMachine(consoleType, command.split(" "));
		return "";
		
	}
	
	public String filterCommand(String command){
		//vagrant destroy
		StringBuilder stringConcat = new StringBuilder();
		int indexOfDestroy = command.indexOf("destroy");
		int indexOfForce = command.indexOf("--force");
		if((indexOfDestroy > -1) && (indexOfForce == -1)){
			stringConcat.append(command.substring(0, indexOfDestroy + 7));
			stringConcat.append(" --force");
			stringConcat.append(command.substring(indexOfDestroy + 7, command.length()));
			command = stringConcat.toString();
		}
		return command;
	}
	
	public static void main(String args[]) throws IOException, InterruptedException{
		WindowsShellProcessor proc = new WindowsShellProcessor();
		System.out.println(proc.filterCommand("vagrant destroy mysql")+"'");
	}
}
