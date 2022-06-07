package com.bryan.hello.preword.info;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bryan.hello.preword.info.model.City;
import com.bryan.hello.preword.info.model.Project;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("info")
public class InfoController {
	
//	//�ʵ� ���Թ��
//	@Autowired
//	private infoService infoService;
//	
//	@GetMapping("/info")
//	public Object projectInfo() {
//		log.debug("/info start");
////		Project project = new Project();
////		project.projectName = "preword";
////		project.author = "hello-bryan";
////		project.createDate = new Date();
//		
//		Project project = infoService.getProjectInfo();
//		
//		//log.info("return {}", project.toString());
//		return project;
//	}
	
	
	private InfoService infoService;
	
	//������ ���Թ��
	@Autowired  // spring 4.3 ���� �̻���ʹ� ���� ����
	public InfoController(InfoService infoService) {
		this.infoService = infoService;
	}
	
	
	@GetMapping("project")
	public Object projectInfo() {
		log.debug("/info start");
		Project project = infoService.getProjectInfo();
		return project;
	}
	
	
	@GetMapping("custom")
	public String customJson() {
		JsonObject jo = new JsonObject();
		
		jo.addProperty("projectName", "preword");
		jo.addProperty("author", "hello-hryan");
		jo.addProperty("createdDate", new Date().toString());
		
		JsonArray ja = new JsonArray();
		for(int i=0; i<5; i++) {
			JsonObject jobj = new JsonObject();
			jobj.addProperty("prop"+i, i);
			ja.add(jobj);
		}
		jo.add("follower", ja);
		return jo.toString();
	}
	
	@GetMapping("cityList")
	public Object cityList() {
		log.debug("/cityList start");
		List<City> cityList = infoService.getCityList();
		return cityList;
	}
	
	
	//localhost:8080/info/cityListByCode/KOR/3000000 �̷������� url�ּҷ� ǥ��
	@GetMapping("cityListByCode/{countryCode}/{population}")
	public Object cityByCountryCode(@PathVariable("countryCode") String ctCode, @PathVariable("population") int population) {
		log.debug("countryCode = {}, population {}", ctCode, population);
		List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
		return cityList;
	}
	
	//http://localhost:8080/info/cityListByCode?countryCode=KOR&population=1000000 �̷������� url�ּҷ� ǥ��
	//http://localhost:8080/info/cityListByCode?countryCode=KOR �̷������� population���� �����ص� �� default�� 0���� ��������
//	@GetMapping("cityListByCode")
//	public Object cityByCountryCode(@RequestParam("countryCode") String ctCode
//			, @RequestParam(value="population", required = false, defaultValue = "0") int population) {
//		log.debug("countryCode = {}, population = {}", ctCode, population);
//		List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
//		return cityList;
//	}
}
