package com.boot.s1.repository;

import com.boot.s1.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //조회한 댓글 페이징 처리
    @Query("select r from Reply  r where r.board.bno = :bno")
    Page<Reply> listOfBoard(Long bno, Pageable pageable);
}
