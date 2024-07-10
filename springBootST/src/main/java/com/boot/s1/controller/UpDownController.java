package com.boot.s1.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Log4j2
public class UpDownController {

    @Value("${com.boot.s1.upload.path}")
    private String upLoadPath;

    @Operation(summary = "POST", description = "POST 방식으로 파일 등록함")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> upload(
                                                @RequestPart("files")
                                                List<MultipartFile> files
                                                ) {
        log.info("요청한 파일: {}", files);
        Map<String, Object> response = new HashMap<>();
            for (MultipartFile file : files) {
                String originalName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(upLoadPath, uuid + "_" + originalName);
                try {
                    file.transferTo(savePath);
                    log.info("업로드 된 파일: " + savePath.toString());
                } catch (IOException e) {
                    log.error("업로드 실패", e);
                    response.put("결과" , "업로드 성공");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        response.put("결과", "업로드 성공");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
