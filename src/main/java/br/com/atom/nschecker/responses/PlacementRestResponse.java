package br.com.atom.nschecker.responses;

import java.util.ArrayList;

import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.common.responses.Response;
import br.com.atom.nschecker.classes.VNFC;

public class PlacementRestResponse {

	boolean created;
	String message;
	Response<ArrayList<VNFC>> jsondata; 
	ConsistencyResponse consistency;
	
	public PlacementRestResponse() {
		this.created = false;
		this.message = "default message";
		this.jsondata = new Response<ArrayList<VNFC>>();
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
	
	public Response<ArrayList<VNFC>> getJsondata() {
		return jsondata;
	}

	public void setJsondata(Response<ArrayList<VNFC>> jsondata) {
		this.jsondata = jsondata;
	}

	public ConsistencyResponse getConsistency() {
		return consistency;
	}
	
	public void setConsistency(ConsistencyResponse consistency) {
		this.consistency = consistency;
	}		
	
}
