package com.boot.s1.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    private long bno;

//  등록시 비어 있지 않도록 처리
    @NotEmpty
    @Size(min = 3, max = 100)
    private String title;

    @NotEmpty
    private String writer;

    @NotEmpty
    private String content;

    private List<String> fileNames;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
