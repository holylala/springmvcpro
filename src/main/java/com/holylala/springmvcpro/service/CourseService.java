package com.holylala.springmvcpro.service;

import org.springframework.stereotype.Service;
import com.holylala.springmvcpro.model.Course;


@Service
public interface CourseService {
	
	
	Course getCoursebyId(Integer courseId);
	

	
	

}
