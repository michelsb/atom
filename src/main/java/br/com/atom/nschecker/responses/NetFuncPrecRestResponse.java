package br.com.atom.nschecker.responses;

import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.common.responses.Response;
import br.com.atom.nschecker.dtos.NetFuncPrecRestDto;

public class NetFuncPrecRestResponse {

	boolean created;
	String message;
	Response<NetFuncPrecRestDto> jsondata; 
	ConsistencyResponse consistency;
	
	public NetFuncPrecRestResponse() {
		this.created = false;
		this.message = "default message";
		this.jsondata = new Response<NetFuncPrecRestDto>();
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
	
	public Response<NetFuncPrecRestDto> getJsondata() {
		return jsondata;
	}

	public void setJsondata(Response<NetFuncPrecRestDto> jsondata) {
		this.jsondata = jsondata;
	}

	public ConsistencyResponse getConsistency() {
		return consistency;
	}
	
	public void setConsistency(ConsistencyResponse consistency) {
		this.consistency = consistency;
	}		
	
}
