package com.boot.s1.service;

import com.boot.s1.dto.BoardDTO;
import com.boot.s1.repository.search.BoardSearch;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.boot.s1.domain.Board;
import com.boot.s1.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

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
            Board board = modelMapper.map(boardDTO, Board.class);
            //boardRepository 주입
            long bno = boardRepository.save(board).getBno();

            return bno;

    }
// 조회 작업
    @Override
    public BoardDTO readOne(Long bno) {

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

//  수정 작업
    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow();

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        boardRepository.save(board);
    }

//  삭제 작업
    @Override
    public void remove(Long bno) {

        boardRepository.deleteById(bno);

    }



}
