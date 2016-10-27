package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zmap.his.mapper.PatientRegistrationMapper;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class TestController {

	@Autowired	
	private PatientRegistrationMapper mapper;
	
	public void test(){
		
	}
}
