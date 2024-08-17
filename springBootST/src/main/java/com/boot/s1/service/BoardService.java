package com.boot.s1.service;

import com.boot.s1.domain.Board;
import com.boot.s1.dto.*;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import java.util.stream.Collectors;

public interface BoardService {

    long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

//  댓글의 숫자까지 처리함
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

//  개시글 이미지 댓글 숫자 처리
    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

//  이미지 엔티티로 변환
    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();

        if(boardDTO.getFileNames() != null) {
            boardDTO.getFileNames().forEach(fileName ->{
//                해당 코드는 파일명에 "_"가 2개 존재하면 작동하지 안음
//                String[] arr = fileName.split("_", 1);
//                board.addImage(arr[0], arr[1]);

//                uuid과 구분되는 "_"를 분리하기 위한 첫번째 "_"만 분리하고 나머지는 파일명으로 저장
                int index = fileName.indexOf("_");
                    String firstPart = fileName.substring(0, index);
                    String secondPart = fileName.substring(index + 1);
                    board.addImage(firstPart, secondPart);
//                log.info();
            });
        }
        return board;
    }

    default BoardDTO entityToDTO(Board board) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();
        List<String> fileNames =
                board.getImageSet().stream().sorted().map(boardImage -> boardImage.getUuid()+
                        "_"+ boardImage.getFileName()).collect(Collectors.toList());

        boardDTO.setFileNames(fileNames);

        return boardDTO;
    }

}
