package com.chabed.spring.thymeleaf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.chabed.spring.thymeleaf.model.DataModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WelcomeController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(WelcomeController.class);

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}
	
	@RequestMapping("about")
	public String aboutPage(Map<String, Object> model) {
		
		//this section for get jeson data  get jeson data
		/*List<DataModel> datamodellist = getJesonData();
		model.put("message", datamodellist);*/
		
		
		return "about";
	}
	
	
	public  List<DataModel> getJesonData() {
		
		List<DataModel> model = new ArrayList<DataModel>();
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://176.241.191.36:9000/rufaida/radiologyOrderByPatNo?patNo=52553814";
		
		String json = restTemplate.getForObject(url, String.class);
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			// convert JSON string to Map
			map = mapper.readValue(json,
					new TypeReference<HashMap<String, Object>>() {
					});
			
			List<LinkedHashMap> testEntities = (List<LinkedHashMap>)map.get("data");
			
			//String s = testEntities.get(0).getT_DRUG_THERAP_CLS_CODE();
			
			
			for(int i=0; i<testEntities.size(); i++){
				String patNo =(String) testEntities.get(i).get("patNo");
				String orderNo = (String) testEntities.get(i).get("orderNo");
				String orderDate = (String) testEntities.get(i).get("orderDate");
				String doctorName = (String) testEntities.get(i).get("doctorName");
				String procdureName = (String) testEntities.get(i).get("procdureName");
				
				System.out.println(testEntities.get(i).get("orderNo"));
				
				DataModel m = new DataModel();
				m.setPatNo(patNo);
				m.setOrderNo(orderNo);
				m.setOrderDate(orderDate);
				m.setDoctorName(doctorName);
				m.setProcdureName(procdureName);
				model.add(m);
				
			}

		} catch (Exception e) {
			logger.info("Exception converting {} to map", json, e);
		}
		
		return model;

	}

}