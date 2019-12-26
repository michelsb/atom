package br.com.atom.nsplanner.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.atom.common.responses.ListResponse;
import br.com.atom.common.responses.Response;
import br.com.atom.nsplanner.dtos.GoalDto;
import br.com.atom.nsplanner.dtos.HighLevelPolicyDto;
import br.com.atom.nsplanner.modules.GoalPolicyManagementModule;
import br.com.atom.nsplanner.responses.GoalResponse;
import br.com.atom.nsplanner.responses.HighLevelPolicyResponse;
import br.com.atom.nsplanner.util.NamedClasses;

@RestController
@RequestMapping("/nsplanner/goalapi")
public class GoalAPI {

	@Autowired
	private GoalPolicyManagementModule goalPMM;
	
	@PostMapping(path = "/goal/new-set")
	public ResponseEntity<HighLevelPolicyResponse> createGoal(@Valid @RequestBody HighLevelPolicyDto polDto, BindingResult result) {		
		HighLevelPolicyResponse response = new HighLevelPolicyResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<HighLevelPolicyDto> jsonErrorResponse = new Response<HighLevelPolicyDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		
		goalPMM.start();
		response = goalPMM.createGoals(polDto);
		if (response.isCreated()) {
			goalPMM.saveUpdates();
			goalPMM.stop();
			response.getJsondata().setData(polDto);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			goalPMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
				
	}
	
	@PostMapping(path = "/goal/new")
	public ResponseEntity<GoalResponse> createGoal(@Valid @RequestBody GoalDto goalDto, BindingResult result) {		
		GoalResponse response = new GoalResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<GoalDto> jsonErrorResponse = new Response<GoalDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		
		goalPMM.start();
		response = goalPMM.createGoal(goalDto);
		if (response.isCreated()) {
			goalPMM.saveUpdates();
			goalPMM.stop();
			response.getJsondata().setData(goalDto);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			goalPMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
				
	}
	
//	@PostMapping(path = "/goal/refine-goal")
//	public ResponseEntity<PlanResponse> refineGoal(@Valid @RequestBody GoalDto goalDto, BindingResult result) {		
//		PlanResponse response = new PlanResponse();		
//		if (result.hasErrors()) {			
//			response.setMessage("ERROR: Problems with JSON body!");
//			Response<PlanDto> jsonErrorResponse = new Response<PlanDto>();
//			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
//			response.setJsondata(jsonErrorResponse);
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}		
//		
//		goalPMM.start();
//		response = goalPMM.refineGoal(goalDto);
//		if (response.isCreated()) {
//			//goalPMM.saveUpdates();
//			goalPMM.stop();
//			//response.getJsondata().setData(goalDto);
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		} else {
//			goalPMM.stop();
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}		
//				
//	}
	
	@DeleteMapping(path = "/goal/delete")
	public ResponseEntity<GoalResponse> removeGoal(@Valid @RequestBody GoalDto goalDto, BindingResult result) {		
		GoalResponse response = new GoalResponse();		
		if (result.hasErrors()) {			
			response.setMessage("ERROR: Problems with JSON body!");
			Response<GoalDto> jsonErrorResponse = new Response<GoalDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
		
		goalPMM.start();
		response = goalPMM.removeGoal(goalDto);
		if (response.isCreated()) {
			goalPMM.saveUpdates();
			goalPMM.stop();
			response.getJsondata().setData(goalDto);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			goalPMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
				
	}
	
	//@GetMapping(path = "/goal/list")
	
	@GetMapping(path = "/level/list")
	public ResponseEntity<ListResponse> listLevels() {
		ListResponse response = new ListResponse();
		goalPMM.start();
		response = goalPMM.listGoalData(NamedClasses.LEVEL);
		goalPMM.stop();
		if (response.isRecovered()) {			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
	}
	
	@GetMapping(path = "/attribute/list")
	public ResponseEntity<ListResponse> listAttributes() {
		ListResponse response = new ListResponse();
		goalPMM.start();
		response = goalPMM.listGoalData(NamedClasses.ATTRIBUTE);
		goalPMM.stop();
		if (response.isRecovered()) {			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}		
	}
	
	
	
}
