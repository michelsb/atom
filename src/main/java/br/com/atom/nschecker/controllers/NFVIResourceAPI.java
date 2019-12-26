package br.com.atom.nschecker.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.atom.common.responses.Response;
import br.com.atom.nschecker.dtos.AffinityRestDto;
import br.com.atom.nschecker.dtos.AntiAffinityRestDto;
import br.com.atom.nschecker.dtos.NFVIDto;
import br.com.atom.nschecker.dtos.NetFuncPrecRestDto;
import br.com.atom.nschecker.modules.NFVIResourceManagentModule;
import br.com.atom.nschecker.modules.PolicyManagementModule;
import br.com.atom.nschecker.responses.AffinityRestResponse;
import br.com.atom.nschecker.responses.AntiAffinityRestResponse;
import br.com.atom.nschecker.responses.NetFuncPrecRestResponse;
import br.com.atom.nschecker.responses.TopologyResponse;

@RestController
@RequestMapping("/nschecker/api")
public class NFVIResourceAPI {

	@Autowired
	private NFVIResourceManagentModule nfviRMM;
	
	@Autowired
	private PolicyManagementModule policyMM;
	
	@PostMapping(path = "/topo/new")
	public ResponseEntity<TopologyResponse> createTopology(@Valid @RequestBody NFVIDto nfviTopo, BindingResult result) {		
		TopologyResponse response = new TopologyResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<NFVIDto> jsonErrorResponse = new Response<NFVIDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		nfviRMM.start();
		response = nfviRMM.createTopology(nfviTopo);
		if (response.isCreated()) {
			nfviRMM.saveUpdates();
			nfviRMM.stop();
			response.getJsondata().setData(nfviTopo);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			nfviRMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
	}
	
	@PostMapping(path = "/netfuncprec/new")
	public ResponseEntity<NetFuncPrecRestResponse> createNetFuncPrecRest(@Valid @RequestBody NetFuncPrecRestDto netFuncPrec, BindingResult result) {		
		NetFuncPrecRestResponse response = new NetFuncPrecRestResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<NetFuncPrecRestDto> jsonErrorResponse = new Response<NetFuncPrecRestDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		policyMM.start();
		response = policyMM.createNetFuncPrecRest(netFuncPrec.getNetFuncPrec());
		if (response.isCreated()) {
			policyMM.saveUpdates();
			policyMM.stop();
			response.getJsondata().setData(netFuncPrec);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			policyMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
	}
	
	@PostMapping(path = "/affinity/new")
	public ResponseEntity<AffinityRestResponse> createAffinityRestToNetFunctions(@Valid @RequestBody AffinityRestDto affRest, BindingResult result) {		
		AffinityRestResponse response = new AffinityRestResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<AffinityRestDto> jsonErrorResponse = new Response<AffinityRestDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		policyMM.start();
		response = policyMM.createAffinityRestToNetFunctions(affRest);
		if (response.isCreated()) {
			policyMM.saveUpdates();
			policyMM.stop();
			response.getJsondata().setData(affRest);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			policyMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
	}
	
	@PostMapping(path = "/antiaffinity/new")
	public ResponseEntity<AntiAffinityRestResponse> createAntiAffinityRestToNetFunctions(@Valid @RequestBody AntiAffinityRestDto antiAffRest, BindingResult result) {		
		AntiAffinityRestResponse response = new AntiAffinityRestResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<AntiAffinityRestDto> jsonErrorResponse = new Response<AntiAffinityRestDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		policyMM.start();
		response = policyMM.createAntiAffinityRestToNetFunctions(antiAffRest);
		if (response.isCreated()) {
			policyMM.saveUpdates();
			policyMM.stop();
			response.getJsondata().setData(antiAffRest);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			policyMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
	}
	
	/*@GetMapping
	public ResponseEntity<String> listar() {
		return ResponseEntity.status(HttpStatus.OK).body("ENTROU");
	}*/
	
}
