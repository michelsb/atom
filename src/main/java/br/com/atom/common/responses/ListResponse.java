package br.com.atom.common.responses;

import java.util.ArrayList;

import br.com.atom.common.dtos.IndividualDto;

public class ListResponse {

	boolean recovered;
	String message;
	Response<ArrayList<IndividualDto>> jsondata; 
	
	public ListResponse() {
		this.recovered = false;
		this.message = "default message";
		this.jsondata = new Response<ArrayList<IndividualDto>>();
	}

	public boolean isRecovered() {
		return recovered;
	}

	public void setRecovered(boolean recovered) {
		this.recovered = recovered;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Response<ArrayList<IndividualDto>> getJsondata() {
		return jsondata;
	}

	public void setJsondata(Response<ArrayList<IndividualDto>> jsondata) {
		this.jsondata = jsondata;
	}	
	
}
