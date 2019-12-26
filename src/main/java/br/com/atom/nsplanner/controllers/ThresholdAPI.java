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
import br.com.atom.nsplanner.dtos.LowLevelPolicyDto;
import br.com.atom.nsplanner.modules.ThresholdManagementModule;
import br.com.atom.nsplanner.responses.LowLevelPolicyResponse;
import br.com.atom.nsplanner.util.NamedClasses;

@RestController
@RequestMapping("/nsplanner/thresholdapi")
public class ThresholdAPI {

	@Autowired
	private ThresholdManagementModule thrMM;

	// @PostMapping(path = "/policy/new")
	// @GetMapping(path = "/policy/list")

	@PostMapping(path = "/policy/new")
	public ResponseEntity<LowLevelPolicyResponse> createPolicy(@Valid @RequestBody LowLevelPolicyDto policy,
			BindingResult result) {
		LowLevelPolicyResponse response = new LowLevelPolicyResponse();
		if (result.hasErrors()) {
			response.setMessage("ERROR: Problems with JSON body!");
			Response<LowLevelPolicyDto> jsonErrorResponse = new Response<LowLevelPolicyDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		thrMM.start();
		response = thrMM.createPolicy(policy);
		if (response.isCreated()) {
			thrMM.saveUpdates();
			thrMM.stop();
			response.getJsondata().setData(policy);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			thrMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}

	@DeleteMapping(path = "/policy/delete")
	public ResponseEntity<LowLevelPolicyResponse> removePolicy(@Valid @RequestBody LowLevelPolicyDto policy,
			BindingResult result) {
		LowLevelPolicyResponse response = new LowLevelPolicyResponse();
		if (result.hasErrors()) {
			response.setMessage("ERROR: Problems with JSON body!");
			Response<LowLevelPolicyDto> jsonErrorResponse = new Response<LowLevelPolicyDto>();
			result.getAllErrors().forEach(error -> jsonErrorResponse.getErrors().add(error.getDefaultMessage()));
			response.setJsondata(jsonErrorResponse);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		thrMM.start();
		response = thrMM.removePolicy(policy);
		if (response.isCreated()) {
			thrMM.saveUpdates();
			thrMM.stop();
			response.getJsondata().setData(policy);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			thrMM.stop();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}

	@GetMapping(path = "/metric/perfchecker/list")
	public ResponseEntity<ListResponse> listPerfCheckerMetrics() {
		ListResponse response = new ListResponse();
		thrMM.start();
		response = thrMM.listThresholdData(NamedClasses.PERFCHECKERMETRIC);
		thrMM.stop();
		if (response.isRecovered()) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@GetMapping(path = "/metric/base/list")
	public ResponseEntity<ListResponse> listBaseMetrics() {
		ListResponse response = new ListResponse();
		thrMM.start();
		response = thrMM.listThresholdData(NamedClasses.BASEMETRIC);
		thrMM.stop();
		if (response.isRecovered()) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@GetMapping(path = "/action/healing/list")
	public ResponseEntity<ListResponse> listHealingActions() {
		ListResponse response = new ListResponse();
		thrMM.start();
		response = thrMM.listThresholdData(NamedClasses.HEALINGACTION);
		thrMM.stop();
		if (response.isRecovered()) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@GetMapping(path = "/action/scaling/list")
	public ResponseEntity<ListResponse> listScalingActions() {
		ListResponse response = new ListResponse();
		thrMM.start();
		response = thrMM.listThresholdData(NamedClasses.SCALINGACTION);
		thrMM.stop();
		if (response.isRecovered()) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
