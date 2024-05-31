package com.boot.s1.controller;

import com.boot.s1.dto.PageRequestDTO;
import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.boot.s1.service.BoardService;
import com.boot.s1.dto.BoardDTO;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

    }
}
