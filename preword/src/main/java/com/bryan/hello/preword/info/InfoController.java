package com.bryan.hello.preword.info;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bryan.hello.preword.info.model.Project;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
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
	
	
	private infoService infoService;
	
	//생성자 주입방식
	@Autowired  // spring 4.3 버전 이상부터는 생략 가능
	public InfoController(infoService infoService) {
		this.infoService = infoService;
	}
	
	
	@GetMapping("/info")
	public Object projectInfo() {
		log.debug("/info start");
		Project project = infoService.getProjectInfo();
		return project;
	}
	
	
	@GetMapping("/info2")
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
}
