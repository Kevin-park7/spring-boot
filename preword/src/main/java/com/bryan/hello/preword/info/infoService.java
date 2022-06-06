package com.bryan.hello.preword.info;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.bryan.hello.preword.info.model.Project;

@Service
public class infoService {
	
	public Project getProjectInfo() {
		
		Project project = new Project();
		project.projectName = "preword";
		project.author = "hello-bryan";
		project.createDate = new Date();
		
		return project;
	}
}
