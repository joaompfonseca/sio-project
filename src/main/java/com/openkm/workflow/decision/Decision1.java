package com.openkm.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.openkm.bean.form.Select;

public class Decision1 implements DecisionHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(ExecutionContext executionContext) throws Exception {
		
		Select s = (Select) executionContext.getContextInstance().getVariable("options");
		
		String value = s.getValue();
		
		String d = "parecer";
		
		if(value.equals("Positiva")){
			executionContext.getContextInstance().setVariable("Resposta", "Deferido");
			d = "direta";
		} else if (value.equals("Negativa")) {
			executionContext.getContextInstance().setVariable("Resposta", "Recusado");
			d = "direta";
		}
		
		System.out.println(d);
		
		return d;
	}

}
