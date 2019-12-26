package br.com.atom.nschecker.responses;

import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.common.responses.Response;
import br.com.atom.nschecker.dtos.AffinityRestDto;

public class AffinityRestResponse {

	boolean created;
	String message;
	Response<AffinityRestDto> jsondata; 
	ConsistencyResponse consistency;
	
	public AffinityRestResponse() {
		this.created = false;
		this.message = "default message";
		this.jsondata = new Response<AffinityRestDto>();
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
	
	public Response<AffinityRestDto> getJsondata() {
		return jsondata;
	}

	public void setJsondata(Response<AffinityRestDto> jsondata) {
		this.jsondata = jsondata;
	}

	public ConsistencyResponse getConsistency() {
		return consistency;
	}
	
	public void setConsistency(ConsistencyResponse consistency) {
		this.consistency = consistency;
	}		
	
}
