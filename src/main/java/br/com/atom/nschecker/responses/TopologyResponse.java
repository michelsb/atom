package br.com.atom.nschecker.responses;

import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.common.responses.Response;
import br.com.atom.nschecker.dtos.NFVIDto;

public class TopologyResponse {

	boolean created;
	String message;
	Response<NFVIDto> jsondata; 
	ConsistencyResponse consistency;
	
	public TopologyResponse() {
		this.created = false;
		this.message = "default message";
		this.jsondata = new Response<NFVIDto>();
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
	
	public Response<NFVIDto> getJsondata() {
		return jsondata;
	}

	public void setJsondata(Response<NFVIDto> jsondata) {
		this.jsondata = jsondata;
	}

	public ConsistencyResponse getConsistency() {
		return consistency;
	}
	
	public void setConsistency(ConsistencyResponse consistency) {
		this.consistency = consistency;
	}		
	
}
