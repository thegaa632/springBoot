package com.boot.s1.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    private long bno;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
