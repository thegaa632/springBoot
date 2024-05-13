package com.boot.s1.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


//기존의 스프링 mvc 패턴방식
@Controller
@Log4j2
public class SampleController {
	
	@GetMapping("/Hellow")
	public void hellow(Model model) {
		
		log.info("----------hellow----------");
		
		model.addAttribute("test", "hellow");
	}
	
}
