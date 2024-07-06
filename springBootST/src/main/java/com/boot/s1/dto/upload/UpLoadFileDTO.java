package com.boot.s1.dto.upload;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class UpLoadFileDTO {

    private List<MultipartFile> files;

    private String fileName;
}
