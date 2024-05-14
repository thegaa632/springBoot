package com.boot.s1.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


//기존의 스프링 mvc 패턴방식
@Controller
@Log4j2
public class SampleController {
	
//	내부 클래스 SampleDTO 정의
	class SampleDTO {
		private String p1,p2,p3;
		
		public String getP1() {
			return p1;
		}
		public String getP2() {
			return p2;
		}
		public String getP3() {
			return p3;
		}
	}
	
	@GetMapping("/hellow")
	public void hellow(Model model) {
		
		log.info("----------hellow----------");
		
		model.addAttribute("test", "hellow");
	}
	
	
//	@GetMapping("/ex/ex1")
//	public void ex1(Model model) {
//		List<String> List = Arrays.asList("aaa", "bbb", "ccc", "ddd");
//		
//		model.addAttribute("List", List);
//		
//	}
	
	@GetMapping("/ex/ex2")
	public void ex2(Model model) {
//		1 ~ 9 까지의 문자열에 Data를 붙여 Data1 ~ Data9 까지의 배열을 만들어 리스트로 변환
		List<String> strList = IntStream.range(1, 10)
				.mapToObj(i -> "Data" + i)
				.collect(Collectors.toList());
//		model에 추가
		model.addAttribute("List", strList);

//		map에 담아 model에 추가
		Map<String, String> map = new HashMap<>();
		map.put("A", "AAAA");
		map.put("B", "BBBB");
		
		model.addAttribute("map", map);
		
//		sampleDTO를 model에 추가
		SampleDTO sampleDTO = new SampleDTO();
		sampleDTO.p1 = "Value -- p1";
		sampleDTO.p2 = "Value -- p2";
		sampleDTO.p3 = "Value -- p3";
		
		model.addAttribute("dto", sampleDTO);
				
	}
	
	@GetMapping("/ex/ex3")
	public void ex3(Model model) {
		
		model.addAttribute("arr", new String[] {"ABC", "DEF", "GHI"});
	}
}
