package com.boot.s1.repository;

import com.boot.s1.domain.Board;
import com.boot.s1.domain.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void InsertTest() {

        //실제 있는 bno 사용
        Long bno = 100L;

        Board board = Board.builder().bno(bno).build();

        for(int i = 1; i < 91 ; i++) {
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("댓글 적기")
                    .replyer("작성자1")
                    .build();
            replyRepository.save(reply);
        }
    }

    @Test
    public void boardRepliesTest() {
        Long bno = 100L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
        result.getContent().forEach(reply -> {
            log.info("reply = " + reply);
        });
    }
}
