package com.boot.s1.serviceTest;

import com.boot.s1.dto.BoardDTO;
import com.boot.s1.dto.PageRequestDTO;
import com.boot.s1.dto.PageResponseDTO;
import com.boot.s1.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class serviceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void registerTest() {

        log.info("registerTest : " + boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("Sample Writer...")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno : " + bno);
    }

    @Test
    public void modifyTest() {
//      변경할 데이터 title, content
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("Update ... 101")
                .content("Update content ... 101")
                .build();

        boardService.modify(boardDTO);

        log.info("modifyTest : " + boardDTO);
    }

    @Test
    public void deleteTest() {
//      삭제시 필요 데이터 bno
        Long bno = 100L;

        boardService.remove(bno);

        try {
            BoardDTO result = boardService.readOne(bno);
            log.info("result : " + result);
        } catch (Exception e) {
            log.error("정상 삭제 됨");

        }
    }

    @Test
    public void ListTest() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info("responseDTO : " + responseDTO);
    }
}
