package com.boot.s1.controller;

import com.boot.s1.dto.ReplyDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/replies")
@Log4j2
public class ReplyController {

    @Operation(summary = "Replies POST", description = "POST 방식으로 댓글 등록함")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(
            @Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult
            ) throws BindException {
        log.info("replyDTO : " + replyDTO);
//      오류시 아래의 코드로 빠지게
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", 111L);

        return resultMap;
    }

}
