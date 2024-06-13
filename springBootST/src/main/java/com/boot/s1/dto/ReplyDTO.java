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
public class ReplyDTO {
    private Long rno;
    //어떤 게시물에 댓글을 달았는지 확인
    private Long tno;
    private String replyText;
    private String replyer;
    private LocalDateTime regDate, modDate;

}
