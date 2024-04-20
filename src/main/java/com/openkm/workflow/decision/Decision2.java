package com.openkm.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.openkm.bean.form.Select;

public class Decision2 implements DecisionHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(ExecutionContext executionContext) throws Exception {
		
		Select s = (Select) executionContext.getContextInstance().getVariable("options");
		
		String value = s.getValue();
		
		System.out.println(value);
		
		return value;
	}

}
