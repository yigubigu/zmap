package com.zmap.esb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmap.his.model.PatientRegistration;
import com.zmap.his.mapper.PatientRegistrationMapper;


@Service
public class DataSyncService {

	@Autowired 
	private PatientRegistrationMapper patientRegistrationMapper;
	
	public void syncPatientRegistration() {
		PatientRegistration patrientRegistration = this.patientRegistrationMapper.selectTop1();
		//call websevice to sync to mysql database 
		
	}
}
