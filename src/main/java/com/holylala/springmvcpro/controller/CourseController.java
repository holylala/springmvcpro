package com.holylala.springmvcpro.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.holylala.springmvcpro.model.Course;
import com.holylala.springmvcpro.service.CourseService;
import com.holylala.springmvcpro.controller.CourseController;



@Controller
@RequestMapping("/courses")
// /courses/**
public class CourseController {
	
	private static Logger log = LoggerFactory.getLogger(CourseController.class);

	private CourseService courseService;

	@Autowired
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	
	//本方法将处理 /courses/view?courseId=123 形式的URL
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String viewCourse(@RequestParam("courseId") Integer courseId,
			Model model) {
		log.debug("In viewCourse, courseId = {}", courseId);
		Course course = courseService.getCoursebyId(courseId);
		model.addAttribute(course);
		return "course_overview";
	}
	
	//本方法将处理 /courses/view2/123 形式的URL
	@RequestMapping("/view2/{courseId}")
	public String viewCourse2(@PathVariable("courseId") Integer courseId,
			Map<String, Object> model) {
		
		log.debug("In viewCourse2, courseId = {}", courseId);
		Course course = courseService.getCoursebyId(courseId);
		model.put("course",course);
		return "course_overview";
	}
	
	//本方法将处理 /courses/view3?courseId=123 形式的URL
	@RequestMapping("/view3")
	public String viewCourse3(HttpServletRequest request) {
		
		Integer courseId = Integer.valueOf(request.getParameter("courseId"));		
		Course course = courseService.getCoursebyId(courseId);
		request.setAttribute("course",course);
		
		return "course_overview";
	}
	//跳转到添加课程界面的方法 多一个 add的请求参数 /courses/admin?add
	@RequestMapping(value="/admin", method = RequestMethod.GET, params = "add")
	public String createCourse(){
		return "course_admin/edit";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String  doSave(@ModelAttribute Course course){		
		
		log.debug("Info of Course:");
		log.debug(ReflectionToStringBuilder.toString(course));
		
		//在此进行业务操作，比如数据库持久化
		course.setCourseId(123);
		return "redirect:view2/"+course.getCourseId();
	}
	//http://localhost:8080/courses/upload
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public String showUploadPage(@RequestParam(value= "multi", required = false) Boolean multi){	
		if(multi != null && multi){
			return "course_admin/multifile";	
		}
		return "course_admin/file";		
	}
	
	@RequestMapping(value="/doUpload", method=RequestMethod.POST)
	//file 是表单中的 name 为 file的元素
	public String doUploadFile(@RequestParam("file") MultipartFile file) throws IOException{
		
		if(!file.isEmpty()){
			log.debug("Process file: {}", file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File("c:\\temp\\imooc\\", System.currentTimeMillis()+ file.getOriginalFilename()));
		}
		
		return "success";
	}
	/*
	@RequestMapping(value="/doUpload2", method=RequestMethod.POST)
	public String doUploadFile2(MultipartHttpServletRequest multiRequest) throws IOException{
		
		Iterator<String> filesNames = multiRequest.getFileNames();
		while(filesNames.hasNext()){
			String fileName =filesNames.next();
			MultipartFile file =  multiRequest.getFile(fileName);
			if(!file.isEmpty()){
				log.debug("Process file: {}", file.getOriginalFilename());
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File("c:\\temp\\imooc\\", System.currentTimeMillis()+ file.getOriginalFilename()));
			}
			
		}
		
		return "success";
	}
	

	{"courseId":123,"title":"深入浅出Java多线程","imgPath":"resources/imgs/course-img.jpg","learningNum":12345,"duration":7200,"level":2,"levelDesc":"中级","descr":"多线程是日常开发中的常用知识，也是难用知识。bala bala...","chapterList":[{"id":1,"courseId":123,"order":1,"title":"第1章 多线程背景知识介绍","descr":"本章将介绍与多线程编程相关的背景概念"},{"id":2,"courseId":123,"order":2,"title":"第2章 Java 线程初体验","descr":"Java语言层面对线程的支持，如何创建，启动和停止线程。如何使用常用的线程方法。用隋唐演义理解线程的代码"},{"id":3,"courseId":123,"order":3,"title":"第3章 Java 线程的正确停止","descr":"本章讨论如何正确的停止一个线程，既要线程停得了，还得线程停得好。"},{"id":4,"courseId":123,"order":4,"title":"第4章 线程交互","descr":"争用条件，线程的交互，及死锁的成因及预防。"},{"id":5,"courseId":123,"order":5,"title":"第5章 进阶展望","descr":"简单介绍 Java 并发相关的类，及常用的多线程编程模型。"}]}
	
	*/

	@RequestMapping(value="/{courseId}",method=RequestMethod.GET)
	public @ResponseBody Course getCourseInJson(@PathVariable Integer courseId){
		//@ResponseBody 会把返回的Course bean 转换成 Json字符串
		//@RequestBody 会把前端json的数据提交到这
		return  courseService.getCoursebyId(courseId);
	}
	//不用@ResponseBody
	//http://localhost:8080/courses/jsontype/123
	@RequestMapping(value="/jsontype/{courseId}",method=RequestMethod.GET)
	public  ResponseEntity<Course> getCourseInJson2(@PathVariable Integer courseId){
		Course course =   courseService.getCoursebyId(courseId);		
		return new ResponseEntity<Course>(course, HttpStatus.OK);
	}

}
