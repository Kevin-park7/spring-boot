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
	
//	//필드 주입방식
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
	
	//생성자 주입방식
	@Autowired  // spring 4.3 버전 이상부터는 생략 가능
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
	
	
	//localhost:8080/info/cityListByCode/KOR/3000000 이런식으로 url주소로 표시
	@GetMapping("cityListByCode/{countryCode}/{population}")
	public Object cityByCountryCode(@PathVariable("countryCode") String ctCode, @PathVariable("population") int population) {
		log.debug("countryCode = {}, population {}", ctCode, population);
		List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
		return cityList;
	}
	
	//http://localhost:8080/info/cityListByCode?countryCode=KOR&population=1000000 이런식으로 url주소로 표시
	//http://localhost:8080/info/cityListByCode?countryCode=KOR 이런식으로 population값을 생략해도 됨 default로 0값을 설정했음
//	@GetMapping("cityListByCode")
//	public Object cityByCountryCode(@RequestParam("countryCode") String ctCode
//			, @RequestParam(value="population", required = false, defaultValue = "0") int population) {
//		log.debug("countryCode = {}, population = {}", ctCode, population);
//		List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
//		return cityList;
//	}
}
