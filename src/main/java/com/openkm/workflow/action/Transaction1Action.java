package com.openkm.workflow.action;

import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.openkm.api.OKMFolder;
import com.openkm.bean.Folder;

public class Transaction1Action implements ActionHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(ExecutionContext executionContext) throws Exception {
		
		int nReq = 1;
		
		// Get all folders 
		List<Folder> paths = OKMFolder.getInstance().getChildren(null, "/okm:root/");
		
		if(paths.size() != 0){
			for(Folder fld: paths){
				String path = fld.getPath();
				String[] temp = path.split("/");
				String fname = temp[temp.length - 1];
			
				if(fname.matches("\\d+")) {
					int l = Integer.parseInt(fname);
					if (l >= nReq){
						nReq = l;
					}
				}
			}
		} 
		
		int newf;
		
		if(paths.size() == 0){
			newf = 1;
		} else {
			newf = nReq + 1;
		}
		
		String fldPath = "/okm:root/" + String.valueOf(newf);
		// Create Folder
		try{
			OKMFolder.getInstance().createSimple(null, fldPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Move to folder
		String uuid = (String) executionContext.getContextInstance().getVariable("uuid");
		String path = OKMFolder.getInstance().getPath(null, uuid);
		try {
			OKMFolder.getInstance().move(null, path, fldPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		executionContext.getToken().signal();
	}
	
}