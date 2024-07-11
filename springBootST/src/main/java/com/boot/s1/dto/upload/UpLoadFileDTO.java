package com.boot.s1.dto.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpLoadFileDTO {

    private List<MultipartFile> files;

}
