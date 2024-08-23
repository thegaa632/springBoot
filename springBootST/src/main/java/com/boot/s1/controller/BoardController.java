package com.boot.s1.controller;

import com.boot.s1.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.boot.s1.service.BoardService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Controller", description = "API endpoints")
public class BoardController {

    @Value("${com.boot.s1.upload.path}")
    private String uploadPath;

    private final BoardService boardService;
    @GetMapping("/list")
    @Operation(summary = "Get list", description = "example response")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<BoardListAllDTO> responseDTO = boardService.listWithAll(pageRequestDTO);

//      데이터 받았나 확인
        log.info("List : responseDTO :" + responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

//    등록 파트
//    화면단 표시
    @GetMapping("/register")
    @Operation(summary = "Get register", description = "register response")
    public void registerGET(BoardDTO boardDTO) {

    }
//    실 데이터 처리
    @PostMapping("/register")
    @Operation(summary = "Post register", description = "register data")
    public String registerPOST(
            @ModelAttribute @Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("board POST register");
//      에러 발생시 모든 에러를 redirectAttributes에 추가로 전송함
        if(bindingResult.hasErrors()) {
            log.info("has error...");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register";
        }
        log.info("확인 : " + boardDTO);

        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

//    조회/수정 파트
    @GetMapping({"/read", "/modify"})
    @Operation(summary = "Get Read And Modify", description = "Read And Modify response")
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    @Operation(summary = "Post Modify", description = "Modify data")
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
    @Operation(summary = "Post Reamove", description = "remove data")
    public String remove(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {


        Long bno = boardDTO.getBno();
        log.info("remove post ... " + bno);

        boardService.remove(bno);

//      게시물이 데이터베이스상에서 삭제 되었다면 첨부파일을 삭제
        log.info("파일 이름들 : " + boardDTO.getFileNames());
        List<String> fileNames = boardDTO.getFileNames();

        if(fileNames != null && fileNames.size() > 0) {
            removeFile(fileNames);
        }
        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/list";
    }

    public void removeFile(List<String> files) {

        for(String fileName:files) {
            Resource resource = new FileSystemResource(
                    uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());

                resource.getFile().delete();

                //섬네일 존재시
                if(contentType.startsWith("image")) {
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);

                    thumbnailFile.delete();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        } //end for
    }
}
