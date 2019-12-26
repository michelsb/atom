package br.com.atom.nsplanner.controllers;

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
import br.com.atom.nsplanner.dtos.GoalDto;
import br.com.atom.nsplanner.dtos.PlanDto;
import br.com.atom.nsplanner.modules.PlannerManagementModule;
import br.com.atom.nsplanner.responses.PlanResponse;

@RestController
@RequestMapping("/nsplanner/plannerapi")
public class PlannerAPI {

	@Autowired
	private PlannerManagementModule plannerMM;
	
	@PostMapping(path = "/refine-goal")
	public ResponseEntity<PlanResponse> refineGoal(@Valid @RequestBody GoalDto goalDto, BindingResult result) {		
		PlanResponse response = new PlanResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<PlanDto> jsonErrorResponse = new Response<PlanDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		
		plannerMM.start();
		response = plannerMM.refineGoal(goalDto);
		if (response.isCreated()) {
			//goalPMM.saveUpdates();
			plannerMM.stop();
			//response.getJsondata().setData(goalDto);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			plannerMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
				
	}
	
//	// @PostMapping(path = "/policy/new")
//	// @GetMapping(path = "/policy/list")
//	
//	@PostMapping(path = "/policy/new")
//	public ResponseEntity<LowLevelPolicyResponse> createPolicy(@Valid @RequestBody LowLevelPolicyDto policy, BindingResult result) {
//		LowLevelPolicyResponse response = new LowLevelPolicyResponse();
//		if (result.hasErrors()) {			
//			response.setMessage("ERROR: Problems with JSON body!");
//			Response<LowLevelPolicyDto> jsonErrorResponse = new Response<LowLevelPolicyDto>();
//			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
//			response.setJsondata(jsonErrorResponse);
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}		
//		plannerMM.start();
//		response = plannerMM.createPolicy(policy);
//		if (response.isCreated()) {
//			plannerMM.saveUpdates();
//			plannerMM.stop();
//			response.getJsondata().setData(policy);
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		} else {
//			plannerMM.stop();
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}
//		
//	}
//	
//	@DeleteMapping(path = "/policy/delete")
//	public ResponseEntity<LowLevelPolicyResponse> removePolicy(@Valid @RequestBody LowLevelPolicyDto policy, BindingResult result) {
//		LowLevelPolicyResponse response = new LowLevelPolicyResponse();
//		if (result.hasErrors()) {			
//			response.setMessage("ERROR: Problems with JSON body!");
//			Response<LowLevelPolicyDto> jsonErrorResponse = new Response<LowLevelPolicyDto>();
//			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
//			response.setJsondata(jsonErrorResponse);
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}		
//		plannerMM.start();
//		response = plannerMM.removePolicy(policy);
//		if (response.isCreated()) {
//			plannerMM.saveUpdates();
//			plannerMM.stop();
//			response.getJsondata().setData(policy);
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		} else {
//			plannerMM.stop();
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}
//		
//	}
	
//	@GetMapping(path = "/metric/perfchecker/list")
//	public ResponseEntity<ListResponse> listPerfCheckerMetrics() {
//		ListResponse response = new ListResponse();
//		plannerMM.start();
//		response = plannerMM.listPlannerData(NamedClasses.PERFCHECKERMETRIC);
//		plannerMM.stop();
//		if (response.isRecovered()) {			
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		} else {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}		
//	}
//	
//	@GetMapping(path = "/metric/base/list")
//	public ResponseEntity<ListResponse> listBaseMetrics() {
//		ListResponse response = new ListResponse();
//		plannerMM.start();
//		response = plannerMM.listPlannerData(NamedClasses.BASEMETRIC);
//		plannerMM.stop();
//		if (response.isRecovered()) {			
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		} else {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}		
//	}
//	
//	@GetMapping(path = "/action/healing/list")
//	public ResponseEntity<ListResponse> listHealingActions() {
//		ListResponse response = new ListResponse();
//		plannerMM.start();
//		response = plannerMM.listPlannerData(NamedClasses.HEALINGACTION);
//		plannerMM.stop();
//		if (response.isRecovered()) {			
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		} else {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}		
//	}
//	
//	@GetMapping(path = "/action/scaling/list")
//	public ResponseEntity<ListResponse> listScalingActions() {
//		ListResponse response = new ListResponse();
//		plannerMM.start();
//		response = plannerMM.listPlannerData(NamedClasses.SCALINGACTION);
//		plannerMM.stop();
//		if (response.isRecovered()) {			
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		} else {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}		
//	}
	
	// @GetMapping(path = "/action/list")
	
}
