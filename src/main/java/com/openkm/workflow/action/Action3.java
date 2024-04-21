package com.openkm.workflow.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.openkm.api.OKMFolder;
import com.openkm.api.OKMDocument;

public class Action3 implements ActionHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(ExecutionContext executionContext) throws Exception {
		
		String nmec = (String) executionContext.getContextInstance().getVariable("nMec");
		
		// Get path of current file
		String uuid = (String) executionContext.getContextInstance().getVariable("uuid");
		String path = OKMFolder.getInstance().getPath(null, uuid);
		
		String[] temp = path.split("/");
		String fp = "/";
		
		for(int i=0; i < temp.length - 1; i++){
			fp = fp + "/" + temp[i];
		}
		
		String p = "/okm:root/students/" + nmec;
		
		OKMFolder.getInstance().createMissingFolders(null, p);
		
		OKMFolder.getInstance().copy(null, fp, p);
		
		String text = "Test";
		InputStream is = new ByteArrayInputStream(text.getBytes());
		
		String[] temp1 = fp.split("/");
		String nr = temp1[temp1.length -1];
		
		String p1 ="/okm:root/students/" + nmec + "/" + nr + "/Funcionario.txt";
		
		OKMDocument.getInstance().createSimple(null, p1, is);
		
		executionContext.getToken().signal();
	}

}
