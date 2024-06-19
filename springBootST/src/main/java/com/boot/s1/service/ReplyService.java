package com.boot.s1.service;

import com.boot.s1.dto.PageRequestDTO;
import com.boot.s1.dto.PageResponseDTO;
import com.boot.s1.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);
}
