package com.boxupp;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.boxupp.responseBeans.VagrantOutput;
import com.boxupp.responseBeans.VagrantStreamError;
import com.boxupp.responseBeans.VagrantStreamOutput;
import com.boxupp.ws.OutputConsole;

public class VagrantOutputStream implements OutputConsole{
	private static LinkedList<VagrantOutput> output = new LinkedList<VagrantOutput>();
	
	public void pushOutput(String data){
		VagrantStreamOutput vagrantStreamOutput = new VagrantStreamOutput();
		vagrantStreamOutput.setOutput(data);
		output.add(vagrantStreamOutput);
	}
	
	public void flushData(){
		output.clear();
	}
	
	public void pushError(String data){
		VagrantStreamError vagrantStreamError = new VagrantStreamError();
		vagrantStreamError.setOutput(data);
		output.add(vagrantStreamError);
	}
	
	public void pushDataTermination(){
		VagrantStreamOutput vagrantStreamOutput = new VagrantStreamOutput();
		vagrantStreamOutput.setOutput("Execution Completed");
		vagrantStreamOutput.setDataEnd(true);
		output.add(vagrantStreamOutput);
	}
		
	public static VagrantOutput pop(){
		try{
			return output.remove();
		}
		catch(NoSuchElementException e){
			VagrantStreamOutput vagrantEmptyOutput = new VagrantStreamOutput();
			vagrantEmptyOutput.setOutput("");
			vagrantEmptyOutput.setType("empty");
			return vagrantEmptyOutput;
		}
	}
}
