package com.openkm.workflow.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.bean.form.Input;
import com.openkm.bean.form.TextArea;
import com.openkm.bean.Document;

import com.openkm.util.MailUtils;

import javax.accessibility.*;

public class Action2 implements ActionHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(ExecutionContext executionContext) throws Exception {
		
		String decision = (String) executionContext.getContextInstance().getVariable("Resposta");
		
		Input assunto = (Input) executionContext.getContextInstance().getVariable("assunto");
		
		TextArea info = (TextArea) executionContext.getContextInstance().getVariable("info");
		
		String uuid = (String) executionContext.getContextInstance().getVariable("uuid");
		String path = OKMFolder.getInstance().getPath(null, uuid);
		String[] temp = path.split("/");
		String number = temp[temp.length - 1];
		
		Document doc = OKMDocument.getInstance().getProperties(null, uuid);
		Calendar temp1 = doc.getCreated();
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date = format1.format(temp1);
		
		String text = "Na sequência do requiremento " + number + " do dia " + date + " relativo ao assunto: " + assunto.getValue() + ", vimos pelo presente informar que sobre o mesmo recaiu o despacho cujo teor a seguir se transcreve " + decision + ". " + info.getValue() ;
		
		try{
			MailUtils.sendMessage("test", "test", "test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
