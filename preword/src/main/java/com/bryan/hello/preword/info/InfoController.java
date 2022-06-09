package com.bryan.hello.preword.info;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@GetMapping("getCityListByCode1/{countryCode}/{population}")
	public Object getCityByCountryCode1(@PathVariable("countryCode") String ctCode, @PathVariable("population") int population) {
		log.debug("countryCode = {}, population {}", ctCode, population);
		List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
		return cityList;
	}
	
	//http://localhost:8080/info/cityListByCode?countryCode=KOR&population=1000000 이런식으로 url주소로 표시
	//http://localhost:8080/info/cityListByCode?countryCode=KOR 이런식으로 population값을 생략해도 됨 default로 0값을 설정했음
	@GetMapping("getCityListByCode2")
	public Object getCityByCountryCode2(@RequestParam("countryCode") String ctCode
			, @RequestParam(value="population", required = false, defaultValue = "0") int population) {
		log.debug("countryCode = {}, population = {}", ctCode, population);
		List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
		return cityList;
	}
	
	//http://localhost:8080/info/cityAdd/TEST/TST/Seoul/10
	@GetMapping("getCityAdd/{name}/{countryCode}/{district}/{population}")
	public Object getCityAdd(@PathVariable(value="name") String name
			, @PathVariable(value="countryCode") String ctCode
			, @PathVariable(value="district") String district
			, @PathVariable(value="population") int population) {
		
		log.debug("name = {}, ctCode = {}, district = {}, population ={}", name, ctCode, district, population);
		
		return "ok";
	}
	
	//http://localhost:8080/info/cityAdd?name=TEST&countryCode=TST&district=Seoul&population=100
	@GetMapping("getCityAdd2")
	public Object getCityAdd2(@RequestParam(value="name", required=true) String name
			, @RequestParam(value="countryCode", required=true) String ctCode
			, @RequestParam(value="district", required=true) String district
			, @RequestParam(value="population", required = false, defaultValue = "0") int population) {
		
		log.debug("name = {}, ctCode = {}, district = {}, population ={}", name, ctCode, district, population);
		
		return "ok";
	}

	//위와 동일하게 사용가능하다
	@GetMapping(value="getCityAdd3")
	public Object getCityAdd3(City city) {
		log.debug("city = {}", city.toString());
		return "ok";
	}
	
	
	
	//@PostMapping 
//	{
//	    "name": "TEST",
//	    "countryCode": "TST",
//	    "district": "Seoul",
//	    "population": 100
//	}
	//postman 프로그램
	@PostMapping(value="postCityAdd")
	public ResponseEntity<City> postCityAdd(@RequestBody City city) {
		log.debug("city = {}", city.toString());
		return new ResponseEntity<>(city, HttpStatus.OK);
	}
	
	//return값을 보내지않을 경우
	@PostMapping(value="postCityAddVoid")
	public ResponseEntity<Void> postCityAddVoid(@RequestBody City city) {
		log.debug("city = {}", city.toString());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//요청을 Query Params로 할 때 (form tag 같은)
	@PostMapping(value="postCityAdd2")
	public ResponseEntity<String> postCityAdd2(String name, String countryCode, String district, Integer population) {
		log.debug("name = {}, ctCode = {}, district = {}, population ={}", name, countryCode, district, population);
	
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	//오류처리
	@PostMapping(value="exCityAdd")
	public ResponseEntity<String> exCityAdd(@RequestBody City city){
			try {
				log.debug("city = {}", city.toString());
				
				log.debug(city.getId().toString());
			}catch(Exception e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@PostMapping(value="insertCityAdd")
	public ResponseEntity<City> insertCityAdd(@RequestBody City city) {
		try {
			log.debug("city = {}", city.toString());
			return new ResponseEntity<>(infoService.insert(city), HttpStatus.OK);
		}catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="updateCityEdit")
	public ResponseEntity<String> updateCityEdit(@RequestBody City city) {
		try {
			log.debug("city = {}", city.toString());
			Integer updatedCnt = infoService.updateById(city);
			return new ResponseEntity<>(String.format("%d updated", updatedCnt), HttpStatus.OK);
		}catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@PostMapping(value="cityDelete")
	public ResponseEntity<String> cityDelete(@RequestParam(value="id") Integer id) {
		try {
			log.debug("city id = {}", id);
			Integer deletedCnt = infoService.deleteById(id);
			return new ResponseEntity<>(String.format("%d deleted", deletedCnt), HttpStatus.OK);
		}catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
