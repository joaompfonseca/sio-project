package com.openkm.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.openkm.api.OKMFolder;

public class Comecar implements DecisionHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(ExecutionContext executionContext) throws Exception {
		
		String uuid = (String) executionContext.getContextInstance().getVariable("uuid");
		String path = OKMFolder.getInstance().getPath(null, uuid);
		
		// Get nmec
		String[] temp = path.split("/");
		String fname = temp[temp.length - 1];
				
		String nmec = fname.split("\\.")[0];
		
		String d;
		
		if(nmec.matches("\\d+")){
			d = "comecar";
		} else {
			d = "acabar";
		}
		
		return d;
	}
}
