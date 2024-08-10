package com.boot.s1.controller;

import com.boot.s1.dto.BoardDTO;
import com.boot.s1.dto.upload.UpLoadFileDTO;
import com.boot.s1.dto.upload.UploadResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class UpDownController {

    @Value("${com.boot.s1.upload.path}")
    private String upLoadPath;

    @Operation(summary = "POST", description = "POST 방식으로 파일 등록함")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(@ModelAttribute UpLoadFileDTO upLoadFileDTO) {
        log.info(upLoadFileDTO);

        if(upLoadFileDTO.getFiles() != null) {

            final List<UploadResultDTO> list = new ArrayList<>();

            upLoadFileDTO.getFiles().forEach(multipartFile -> {

                String originalFileName = multipartFile.getOriginalFilename();
                log.info("originalFileName : " + originalFileName);
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(upLoadPath, uuid+"_"+ originalFileName);

                boolean image = false;
                try {
                    multipartFile.transferTo(savePath);

                    //이미지 파일이라면 썸내일 제작
                    if(Files.probeContentType(savePath).startsWith("image")){

                        image = true;

                        File thumbFile = new File(upLoadPath, "s_" + uuid+ "_"+ originalFileName);

                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200,200);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                list.add(UploadResultDTO.builder()
                                .uuid(uuid)
                                .fileName(originalFileName)
                                .img(image)
                                .build()
                );
            }); //end foreach

            return list;
        } // end if

        return null;
    }

    @Operation(summary = "view", description = "GET 방식으로 파일 조회")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        Resource resource = new FileSystemResource(upLoadPath + File.separator + fileName);

        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath() ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @Operation(summary = "remove", description = "DELETE 방식으로 파일 삭제")
    @DeleteMapping("/remove/{fileName}")
    public Map<String, Boolean> removeFile(@PathVariable String fileName) {
        Resource resource = new FileSystemResource(upLoadPath + File.separator + fileName);

        String resourceName = resource.getFilename();

        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;

        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = resource.getFile().delete();

            //썸네일이 존재하면 삭제
            if(contentType.startsWith("image")){

                File thumbnailFile = new File(upLoadPath+ File.separator + "s_" + fileName);

                thumbnailFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultMap.put("result", removed);

        return resultMap;
    }


// DTO를 사용하지 않는 업로드 방식
//    public ResponseEntity<Map<String, Object>> upload(
//                                                @RequestPart("files")
//                                                List<MultipartFile> files
//                                                ) {
//        log.info("요청한 파일: {}", files);
//        Map<String, Object> response = new HashMap<>();
//            for (MultipartFile file : files) {
//                String originalName = file.getOriginalFilename();
//                String uuid = UUID.randomUUID().toString();
//                Path savePath = Paths.get(upLoadPath, uuid + "_" + originalName);
//                try {
//                    file.transferTo(savePath);
//                    log.info("업로드 된 파일: " + savePath.toString());
//
//                    //이미지 파일이라면 썸내일 제작
//                    if(Files.probeContentType(savePath).startsWith("image")){
//                        File thumbFile = new File(upLoadPath, "s_" + uuid+ "_"+ originalName);
//
//                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200,200);
//                    }
//                } catch (IOException e) {
//                    log.error("업로드 실패", e);
//                    response.put("결과" , "업로드 실패");
//                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//                }
//            }
//        response.put("결과", "업로드 성공");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
