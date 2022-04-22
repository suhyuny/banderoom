package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="/board")
@Controller
public class BoardController {
	
	@RequestMapping(value="/notice.do")
	public String Notice(Model model) {
		
		return "board/notice";
	}
	
	@RequestMapping(value="/freeBoard.do")
	public String freeBoard() {
		
		return "board/freeBoard";
	}
	
	@RequestMapping(value="/infomation.do")
	public String infomatino() {
		
		return "board/infomation";
	}
}
