package com.boot.s1.controller;

import com.boot.s1.dto.upload.UpLoadFileDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Log4j2
public class UpDownController {

//    @Value("${com.boot.s1.upload.path}")
//    private String upLoadPath;

    @Operation(summary = "UpLoad POST", description = "POST 방식으로 파일 등록함")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<UpLoadFileDTO> upload(@Parameter(name = "fileName", description = "업로드 파일", in = ParameterIn.QUERY)
                                                @RequestParam(value = "fileName")
                                                    String fileName,
                                                @Parameter(name = "files", description = "업로드 파일")
                                                @RequestParam(value = "files")
                                                UpLoadFileDTO files) {

        UpLoadFileDTO result = UpLoadFileDTO.builder().fileName(fileName).build();

        return new ResponseEntity<>(result, HttpStatus.OK);

//        if(upLoadFileDTO.getFiles() != null) {
//
//            upLoadFileDTO.getFiles().forEach(multipartFile -> {
//
//                String originalName = multipartFile.getOriginalFilename();
//                log.info(multipartFile.getOriginalFilename());
//
//                String uuid = UUID.randomUUID().toString();
//
//                Path savePath = Paths.get(upLoadPath, uuid + "_" + originalName);
//
//                try {
//                    multipartFile.transferTo(savePath); //실제 파일 저장함
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });//end each
//        }

    }
}
