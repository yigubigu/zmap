package com.zmap.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zmap.zmap.mapper.PatientRegistrationMapper;
import com.zmap.zmap.model.PatientRegistration;

@RestController
@RequestMapping("/patient/registrations")
public class PatientRegistrationController {

	@Autowired
	private PatientRegistrationMapper patientRegistrationMapper;
	
	

	@RequestMapping(method=RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody PatientRegistration input) {
		
		int id = this.patientRegistrationMapper.insert(input);
		return null;
		
	}
}
