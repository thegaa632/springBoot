package com.boot.s1.controller;

import com.boot.s1.dto.PageRequestDTO;
import com.boot.s1.dto.PageResponseDTO;
import com.boot.s1.dto.ReplyDTO;
import com.boot.s1.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor //ReplyService 의존성 주입
public class ReplyController {

    private final ReplyService replyService;

    @Operation(summary = "Replies POST", description = "POST 방식으로 댓글 등록함")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(

            @Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult
            ) throws BindException {

//      오류시 아래의 코드로 빠지게
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long rno = replyService.register(replyDTO);

        log.info("rno : " + rno);

        resultMap.put("rno", rno);

        log.info("resultMap : " + resultMap);

        return resultMap;
    }

    //@PathVariable("bno")의 경우 경로의 bno값을 직접 변수로 작성가능
    @Operation(summary = "Replies of Board", description = "GET 방식으로 댓글 등록함")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO) {
        
        //bno 경로지정후 해당 값을 가저옴
        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno, pageRequestDTO);
        
        return responseDTO;
    }

    @Operation(summary = "Read Reply", description = "GET 방식으로 특정한 댓글 조회")
    @GetMapping(value = "/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno) {

        ReplyDTO replyDTO = replyService.read(rno);

        return replyDTO;
    }

    @Operation(summary = "Read Reply", description = "Delete 방식으로 특정한 댓글 조회")
    @DeleteMapping(value = "/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno) {

        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("Remove rno", rno);

        return resultMap;
    }

    @Operation(summary = "Modify Reply", description = "PUT 방식으로 특정한 댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> Modify(@PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO) {

        //번호 일치
        replyDTO.setRno(rno);

        replyService.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

//      여기서 리턴값은 json으로 전송되기 때문에 requestBody를 사용하여야 한다.
        return resultMap;
    }
}
