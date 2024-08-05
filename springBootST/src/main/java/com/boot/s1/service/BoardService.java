package com.boot.s1.service;

import com.boot.s1.domain.Board;
import com.boot.s1.dto.BoardDTO;
import com.boot.s1.dto.BoardListReplyCountDTO;
import com.boot.s1.dto.PageRequestDTO;
import com.boot.s1.dto.PageResponseDTO;

public interface BoardService {

    long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

//   댓글의 숫자까지 처리함
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

//   이미지 엔티티로 변환
    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();

        if(boardDTO.getFileNames() != null) {
            boardDTO.getFileNames().forEach(fileName ->{
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }
        return board;
    }
}
