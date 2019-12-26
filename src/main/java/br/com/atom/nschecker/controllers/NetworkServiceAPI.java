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

import br.com.atom.common.owlmanager.ConflictDetectionModule;
import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.common.responses.Response;
import br.com.atom.nschecker.dtos.AffinityRestDto;
import br.com.atom.nschecker.dtos.AntiAffinityRestDto;
import br.com.atom.nschecker.dtos.NSReqDto;
import br.com.atom.nschecker.dtos.NetworkServiceDto;
import br.com.atom.nschecker.modules.NSManagementModule;
import br.com.atom.nschecker.modules.PolicyManagementModule;
import br.com.atom.nschecker.responses.AffinityRestResponse;
import br.com.atom.nschecker.responses.AntiAffinityRestResponse;
import br.com.atom.nschecker.responses.NSReqResponse;
import br.com.atom.nschecker.responses.NSResponse;
import br.com.atom.nschecker.responses.NetFuncPrecRestResponse;
import br.com.atom.nschecker.responses.PlacementRestResponse;

@RestController
@RequestMapping("/nschecker/nsapi")
public class NetworkServiceAPI {
	
	@Autowired
	private NSManagementModule nsMM;
	
	@Autowired
	private PolicyManagementModule policyMM;
	
	@PostMapping(path = "/ns/new")
	public ResponseEntity<NSResponse> createNetworkService(@Valid @RequestBody NetworkServiceDto networkService, BindingResult result) {		
		NSResponse response = new NSResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<NetworkServiceDto> jsonErrorResponse = new Response<NetworkServiceDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		nsMM.start();
		response = nsMM.createNetworkService(networkService);
		if (response.isCreated()) {
			nsMM.saveUpdates();
			nsMM.stop();
			response.getJsondata().setData(networkService);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			nsMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
	}
	
	/*@PostMapping(path = "/ns/new")
	public ResponseEntity<NSResponse> createNetworkService(@Valid @RequestBody VNFFG networkService, BindingResult result) {		
		NSResponse response = new NSResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<VNFFG> jsonErrorResponse = new Response<VNFFG>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		nsMM.start();
		response = nsMM.createNetworkService(networkService);
		if (response.isCreated()) {
			nsMM.saveUpdates();
			nsMM.stop();
			response.getJsondata().setData(networkService);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			nsMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
	}*/

	@PostMapping(path = "/nsreq/new")
	public ResponseEntity<NSReqResponse> createNSReq(@Valid @RequestBody NSReqDto nsReq, BindingResult result) {		
		NSReqResponse response = new NSReqResponse();
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<NSReqDto> jsonErrorResponse = new Response<NSReqDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		policyMM.start();		
		if (nsReq.getVnfcs().size()>0) {
			PlacementRestResponse placRestResponse = policyMM.createPlacementRest(nsReq.getVnfcs());			
			if (!placRestResponse.isCreated()) {
				response.setMessage(placRestResponse.getMessage());
				response.setConsistency(placRestResponse.getConsistency());
				policyMM.stop();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}		
		}
		//policyMM.reload();
		if (nsReq.getNetFuncPrec().size()>0) {
			NetFuncPrecRestResponse nfrResponse = policyMM.createNetFuncPrecRest(nsReq.getNetFuncPrec()); 
			if (!nfrResponse.isCreated()) {
				response.setMessage(nfrResponse.getMessage());
				response.setConsistency(nfrResponse.getConsistency());
				policyMM.stop();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}		
		}
		//policyMM.reload();
		AffinityRestDto affRestVNFC = nsReq.getAffRestVNFC();
		AffinityRestResponse affRestResponse = policyMM.createAffinityRestToVNFCs(affRestVNFC);
		if (!affRestResponse.isCreated()) {
			response.setMessage(affRestResponse.getMessage());
			response.setConsistency(affRestResponse.getConsistency());
			policyMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		//policyMM.reload();
		AffinityRestDto affRestNetFunc = nsReq.getAffRestNetFunc();
		affRestResponse = policyMM.createAffinityRestToNetFunctions(affRestNetFunc);
		if (!affRestResponse.isCreated()) {
			response.setMessage(affRestResponse.getMessage());
			response.setConsistency(affRestResponse.getConsistency());
			policyMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		//policyMM.reload();		
		AntiAffinityRestDto antiAffRestVNFC = nsReq.getAntiAffRestVNFC();
		AntiAffinityRestResponse antiAffRestResponse = policyMM.createAntiAffinityRestToVNFCs(antiAffRestVNFC);
		if (!affRestResponse.isCreated()) {
			response.setMessage(antiAffRestResponse.getMessage());
			response.setConsistency(antiAffRestResponse.getConsistency());
			policyMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		//policyMM.reload();
		AntiAffinityRestDto antiAffRestNetFunc = nsReq.getAntiAffRestNetFunc();
		antiAffRestResponse = policyMM.createAntiAffinityRestToNetFunctions(antiAffRestNetFunc);
		if (!affRestResponse.isCreated()) {
			response.setMessage(antiAffRestResponse.getMessage());
			response.setConsistency(antiAffRestResponse.getConsistency());
			policyMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		ConflictDetectionModule cdm = new ConflictDetectionModule(policyMM.getOntomanager());
		cdm.start();
		ConsistencyResponse consResponse = cdm.testOntoConsistency();				
		if (consResponse.isConsistent()) {
			response.setCreated(true);
			response.setMessage("Restrictions are consistent!");
			response.setConsistency(consResponse);
		} else {
			response.setMessage("ERROR: Restrictions made Onto-NFV inconsistent!");
			response.setConsistency(consResponse);
		}
		//policyMM.saveUpdates();
		policyMM.stop();
		//response.setCreated(true);
		//response.setMessage("Restrictions are consistent!");
		//response.getJsondata().setData(nsReq);
		return ResponseEntity.status(HttpStatus.OK).body(response);				
	}
	
}
