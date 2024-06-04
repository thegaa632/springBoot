package com.boot.s1.controller;

import com.boot.s1.domain.Board;
import com.boot.s1.dto.PageRequestDTO;
import com.boot.s1.dto.PageResponseDTO;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.boot.s1.service.BoardService;
import com.boot.s1.dto.BoardDTO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

//      데이터 받았나 확인
        log.info("List : responseDTO :" + responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }
//    등록 파트
//    화면단 표시
    @GetMapping("/register")
    public void registerGET(BoardDTO boardDTO) {

    }
//    실 데이터 처리
    @PostMapping("/register")
    public String registerPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("board POST register");
//      에러 발생시 모든 에러를 redirectAttributes에 추가로 전송함
        if(bindingResult.hasErrors()) {
            log.info("has error...");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register";
        }
        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

//    조회/수정 파트
    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, @Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board modify post ... " + boardDTO);

        if (bindingResult.hasErrors()) {
            log.info("has error...");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?" + link;
        }

        boardService.modify(boardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }

//    삭제 파트

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post ... " + bno);

        boardService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/list";
    }
}
