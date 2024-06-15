package com.boot.s1.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {

    private long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;

    private Long replyCount;
}
