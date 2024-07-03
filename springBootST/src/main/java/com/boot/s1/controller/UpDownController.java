package com.boot.s1.controller;

import com.boot.s1.dto.upload.UpLoadFileDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Log4j2
public class UpDownController {

    @Value("${com.boot.s1.upload.path}")
    private String upLoadPath;
//    @Operation(summary = "UpLoad POST", description = "POST 방식으로 파일 등록함")
    @Operation(summary = "UpLoad POST", description = "POST 방식으로 파일 등록함", requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "object", format = "binary"))))
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("files")UpLoadFileDTO upLoadFileDTO) {

        log.info(upLoadFileDTO);

        if(upLoadFileDTO.getFiles() != null) {

            upLoadFileDTO.getFiles().forEach(multipartFile -> {

                String originalName = multipartFile.getOriginalFilename();
                log.info(multipartFile.getOriginalFilename());

                String uuid = UUID.randomUUID().toString();

                Path savePath = Paths.get(upLoadPath, uuid + "_" + originalName);

                try {
                    multipartFile.transferTo(savePath); //실제 파일 저장함
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });//end each
        }

        return "업로드 성공";
    }
}
