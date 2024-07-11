package com.boot.s1.dto.upload;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadResultDTO {

    private String fileName;

    private String uuid;

    private boolean img;

    public String getLink() {

        if(img) {
            return "s_"+ uuid+ "_"+ fileName; //이미지인 경우는 썸네일
        } else {
            return uuid+ "_"+ fileName;
        }
    }
}
