package com.boot.s1.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.boot.s1.dto.ReplyDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
public class ReplyController {

    @Operation(operationId = "Replies POST", description = "POST 방식으로 등록함")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Long>> register(@RequestBody ReplyDTO replyDTO) {

        log.info("replyDTO : " + replyDTO);
        Map<String,Long> resultMap = Map.of("rno", 111L);

        return ResponseEntity.ok(resultMap);
    }

}
