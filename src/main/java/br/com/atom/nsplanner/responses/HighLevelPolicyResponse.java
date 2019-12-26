package br.com.atom.nsplanner.responses;

import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.common.responses.Response;
import br.com.atom.nsplanner.dtos.HighLevelPolicyDto;

public class HighLevelPolicyResponse {

	boolean created;
	String message;
	Response<HighLevelPolicyDto> jsondata; 
	ConsistencyResponse consistency;
	
	public HighLevelPolicyResponse() {
		this.created = false;
		this.message = "default message";
		this.jsondata = new Response<HighLevelPolicyDto>();
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

	public Response<HighLevelPolicyDto> getJsondata() {
		return jsondata;
	}

	public void setJsondata(Response<HighLevelPolicyDto> jsondata) {
		this.jsondata = jsondata;
	}

	public ConsistencyResponse getConsistency() {
		return consistency;
	}

	public void setConsistency(ConsistencyResponse consistency) {
		this.consistency = consistency;
	}
	
}
