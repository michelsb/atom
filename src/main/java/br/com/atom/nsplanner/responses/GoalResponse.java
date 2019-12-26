package br.com.atom.nsplanner.responses;

import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.common.responses.Response;
import br.com.atom.nsplanner.dtos.GoalDto;

public class GoalResponse {

	boolean created;
	String message;
	Response<GoalDto> jsondata; 
	ConsistencyResponse consistency;
	
	public GoalResponse() {
		this.created = false;
		this.message = "default message";
		this.jsondata = new Response<GoalDto>();
		this.consistency = new ConsistencyResponse();
	}
	
	public boolean isCreated() {
		return created;
	}
	
	public void setCreated(boolean created) {
		this.created = created;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}	
	
	public Response<GoalDto> getJsondata() {
		return jsondata;
	}

	public void setJsondata(Response<GoalDto> jsondata) {
		this.jsondata = jsondata;
	}

	public ConsistencyResponse getConsistency() {
		return consistency;
	}
	
	public void setConsistency(ConsistencyResponse consistency) {
		this.consistency = consistency;
	}		
	
}
