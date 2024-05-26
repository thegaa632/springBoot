package com.boot.s1.service;

import com.boot.s1.dto.BoardDTO;

public interface BoardService {

    long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);
}
