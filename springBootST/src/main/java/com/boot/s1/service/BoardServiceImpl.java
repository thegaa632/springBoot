package com.boot.s1.service;

import com.boot.s1.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.boot.s1.domain.Board;
import com.boot.s1.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    //  등록 작업
    @Override
    public long register(BoardDTO boardDTO) {
        if (boardDTO == null) {
            throw new IllegalArgumentException("BoardDTO 가 null 입니다.");
        }
        //modelMapper 주입
        Board board
//                = modelMapper.map(boardDTO, Board.class);

        //boardRepository 주입
        = dtoToEntity(boardDTO);
        long bno = boardRepository.save(board).getBno();

        return bno;

    }
    // 조회 작업
    @Override
    public BoardDTO readOne(Long bno) {

        Optional<Board> result = boardRepository.findByIdWithImages(bno);

        Board board = result.orElseThrow();

        BoardDTO boardDTO = entityToDTO(board);

        return boardDTO;
    }

    //  수정 작업
    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow();

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        //첨부파일 처리
        board.clearImages();

        if(boardDTO.getFileNames() != null) {
            boardDTO.getFileNames().forEach(fileName -> {
                int index = fileName.indexOf("_");
                String firstPart = fileName.substring(0, index);
                String secondPart = fileName.substring(index + 1);
                board.addImage(firstPart, secondPart);
            });
        }
        boardRepository.save(board);
    }

    //  삭제 작업
    @Override
    public void remove(Long bno) {

        boardRepository.deleteById(bno);

    }

    //  검색 작업
    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable();
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

//      Page<Board>를 List<BoardDTO>로 변환
        List<BoardDTO> dtoList = result.getContent().stream().map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    //   댓글 표시
    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    public PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable();

        Page<BoardListAllDTO> result = boardRepository.searchWithAll(types, keyword, pageable);


        return PageResponseDTO.<BoardListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }
}